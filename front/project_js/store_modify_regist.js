function mapreview() {

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };
    // 지도를 생성합니다    
    var map = new kakao.maps.Map(mapContainer, mapOption);

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();
    //var loca =document.getElementById("loca").value;

    var name = document.getElementById("name").value;
    var loca = document.getElementById("loca").value;
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(loca, function (result, status) {

        // 정상적으로 검색이 완료됐으면 
        if (status === kakao.maps.services.Status.OK) {

            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            // 인포윈도우로 장소에 대한 설명을 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">' + name + '</div>'
            });
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
        }
    });
}

window.onload = function () {
    document.getElementById("loca").addEventListener("click", function () { //주소입력칸을 클릭하면
        document.getElementById("map").style.display = "block";
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function (data) { //선택시 입력값 세팅
                document.getElementById("loca").value = data.address; // 주소 넣기
                document.querySelector("input[name=loca]").focus(); //상세입력 포커싱
                mapreview()
            }
        }).open();
    });
}

$(function () {
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL+'member/session',
        success: function(jsonObj){
            if(jsonObj.power==2){
                $('a.store').show()
            }else{
                $('a.store').hide()
            }
            if(jsonObj.power!=0){
                $('a.storeList').hide()
            }
            if(jsonObj.status==1){
                id=jsonObj.id
                $('a.login').hide()
                $('a.signup').hide()
                let nick = jsonObj.nick
                $('span.nickname').html(nick+'님 <br>안녕하세요')
            }else if(jsonObj.status==0){
                $('a.logout').hide()
				$('a.mypage').hide()
				$('span.nickname').hide()
            }
        }
    })


    let stNum = location.search.substring(1)
    //console.log(stNum)
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        method: "post",
        url: backURL+'store/storeload/' + stNum,
        data : stNum,
        success: function(jsonObj){            
            let $storeList = $('div.content_fix')//원본
            let $storeList2 = $('div.content_change')
            $storeList.show()
            let $divsl = $('div.content')

            let store = jsonObj.store
            let cateNum = store[0].cateNum            
            let stName = store[0].stName
            let stLoca = store[0].stLoca
            let stPhone = store[0].stPhone
            let stResNo = store[0].stResNo
            let stDes = store[0].stDes

            console.log(store)
            console.log(cateNum)
            console.log(stName)
            console.log(stDes)
            let $copynoti = $storeList.clone()
            $copynoti.find('select[name=ctnum]').val(cateNum)
            $copynoti.find('input[name=name]').val(stName)
            $copynoti.find('input[name=loca]').val(stLoca)
            $copynoti.find('input[name=phone]').val(stPhone)
            $copynoti.find('input[name=resno]').val(stResNo)
            $copynoti.find('input[name=des]').val(stDes)
            $divsl.append($copynoti)

            let menuFile = jsonObj.menuFile
            console.log(menuFile)
            $(store).each(function(index,n){
                let $copynoti2 = $storeList2.clone()
                console.log("store : "+n)
                console.log(n.stMenu)
                console.log(n.stMenu.menuName)
                console.log(n.stMenu.menuPrice)
                //console.log($copynoti2.html())
                console.log($copynoti2)
                console.log($copynoti2.children('input[name=fd_name]'))

                $copynoti2.find('input[name=fd_name]').val(n.stMenu.menuName)   
                if(index>0){
                    let obj  = $copynoti2.find('input.plus')
                    obj.val('-')
                    obj.removeClass('plus')
                    obj.addClass('mins')
                }
                          
                $copynoti2.find('input[name=fd_price]').val(n.stMenu.menuPrice)

                $divsl.append($copynoti2)
               
            })

            $storeList.hide();
            $storeList2.hide();
           
        }
        
    })








    //이름입력시 값 가져오는지 확인
    $('#name').change(function () {
        let name = $('#name').val()
    })

    let i = 1

    // [ + ] 버튼누를시 이벤트
    let $bp = $(".plus")

    $bp.click(function () {
        $(".content").append("<div class='content_change' >"
            + "메뉴 이름 :  <input name= 'fd_name' >"
            + " <input type='button' class='mins' value='-' ><br>"
            + "음식 가격  : <input name='fd_price'><br>"
            + "이미지 파일 : <input type='file' name='fImg' accept='image/*'>"
            + "<div class='showImg' >"
            + "<img>"
            + " </div>"
            + "</div>");
        i++
    })
    // [ - ] 버튼 누를시 
    let $bm = $(".mins")
    $("div.content").on('click', "div>input.mins", function (e) {
        i--
        $(e.target).parent().remove()
    })

    $('div.content').on('change', 'input[name=fImg]', (e) => {
        //alert("이미지변경됨");
        let imgFile = e.target.files[0]
        let strBlob = URL.createObjectURL(imgFile)
        $(e.target).next('div.showImg').children('img').attr('src', strBlob)
    });

    $('.bt').click(() => {
        let ctnum1 = $('select[name=ctnum]')
        let $name = $('input[name=name]')
        let $loca = $('input[name=loca]')
        let $phone = $('input[name=phone]')
        let $resno = $('input[name=resno]')
        let $des = $('input[name=des]')
        let $fd_name = $('input[name=fd_name]')
        let $fd_price = $('input[name=fd_price]')
        let $fd_img = $('input[name=fd_img]')

        let ctnum = ctnum1.val()

        let Store = {}
        Store.cateNum = ctnum
        Store.stName = $name.val()
        Store.stLoca = $loca.val()
        Store.stPhone = $phone.val()
        Store.stResNo = $resno.val()
        Store.stDes = $des.val()

        let menuArr = []
        let files = []

        let formData = new FormData()

        $("div.content_change").each((index, element) => {
            let Menu = {}
            Menu.menuName = $(element).children('input[name=fd_name]').val()
            Menu.menuPrice = $(element).children('input[name=fd_price]').val()
            menuArr.push(Menu)
            formData.append("files", $(element).children('input[name=fImg]').prop("files")[0])
        })

        Store.stMenuList = menuArr
        formData.append("Store", JSON.stringify(Store))
        
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            processData: false, //파일업로드용 설정
            contentType: false, //파일업로드용 설정	
            method: "post",
            url: backURL + 'store/new',
            data: formData,
            success: function (data) {
                alert("등록 신청 성공!")
                location.href = 'asdasdadsadasd'
            }
        })
    })
})