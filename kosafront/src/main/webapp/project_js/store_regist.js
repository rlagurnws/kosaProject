function openNav() {
    document.querySelector(".mySidenav").style.width = '20%';
    document.querySelector('div.center').style.marginLeft = '20%';

}
function closeNav() {
    document.querySelector(".mySidenav").style.width = '0';
    document.querySelector('div.center').style.marginLeft = '0';
}
function mapreview() {

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };
    console.log(document.getElementById("loca").value);
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
                console.log(document.getElementById("loca").value);
                mapreview()
            }
        }).open();
    });
}


$(function () {
    //이름입력시 값 가져오는지 확인
    $('#name').change(function () {
        let name = $('#name').val()
        alert(name)
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
        console.log(i)

    })
    // [ - ] 버튼 누를시 
    let $bm = $(".mins")
    $("div.content").on('click', "div>input.mins", function (e) {
        i--
        $(e.target).parent().remove()
        console.log(i)
    })

    // $('input[name=fImg]').change((e) => {
    //     //alert("이미지변경됨");
    //     console.log(e.target.files[0])
    //     let imgFile = e.target.files[0
    //     ]
    //     let strBlob = URL.createObjectURL(imgFile)
    //     console.log(strBlob)
    //     $(e.target).next('div.showImg').children('img').attr('src', strBlob)
    // });  

    $('div.content').on('change', 'input[name=fImg]', (e) => {
        //alert("이미지변경됨");
        console.log(e.target.files[0])
        let imgFile = e.target.files[0]
        let strBlob = URL.createObjectURL(imgFile)
        console.log(strBlob)
        $(e.target).next('div.showImg').children('img').attr('src', strBlob)
    });


    // //선택될때라 버튼선택될때 해야함
    // //let $ctnum=$("#ctnum option:selected").val();
    // let ctnum1 = $('select[name=ctnum]')
    // let $name = $('input[name=name]')
    // let $loca = $('input[name=loca]')
    // let $phone = $('input[name=phone]')
    // let $resno = $('input[name=resno]')
    // let $des = $('input[name=des]')
    // let $fd_name = $('input[name=fd_name]')
    // let $fd_price = $('input[name=fd_price]')
    // let $fd_img = $('input[name=fd_img]')

    $('.bt').click(() => {

        //선택될때라 버튼선택될때 해야함
        //let $ctnum=$("#ctnum option:selected").val();
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



        //+버튼 누를때마다 받는객체 구현해야함
        let menuArr = []
        let files = []

        // console.log($("div.content_change:eq(" + 0 + ")").children('input[name=fImg]').prop("files")[0])
        let formData = new FormData()
        // for (j = 0; j < i; j++) {
        //     let food = {}
        //     food.food_name = $("div.content_change:eq(" + j + ")").children('input[name=fd_name]').val()
        //     food.food_price = $("div.content_change:eq(" + j + ")").children('input[name=fd_price]').val()
        //     menuArr.push(food)

        //     //files.push($("div.content_change:eq(" + j + ")").children('input[name=fImg]').prop("files")[0])
        //     // formData.append("files", "div.content_change:eq(" + j + ")").children('input[name=fImg]').prop("files")[0])
        //     formData.append("files", $("div.content_change:eq(" + j + ")").children('input[name=fImg]').prop("files")[0])
        // }


        //console.log(files[0])

        $("div.content_change").each((index, element) => {
            let Menu = {}
            Menu.menuName = $(element).children('input[name=fd_name]').val()
            Menu.menuPrice = $(element).children('input[name=fd_price]').val()
            menuArr.push(Menu)
            formData.append("files", $(element).children('input[name=fImg]').prop("files")[0])
        })

        Store.stMenuList = menuArr
        console.log(Store)
        formData.append("Store", JSON.stringify(Store))
        
        console.log(formData.get("files"))
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            processData: false, //파일업로드용 설정
            contentType: false, //파일업로드용 설정	
            method: "post",
            url: backURL + 'store/new',
            //     ////////Content-Type 명시함
            //    headers: { 'Content-Type': 'application/json' },
            //     ////////객체를 JSON문자열로 변환 :  JSON.stringify()로 감싸주어야 함
            //     data: JSON.stringify(store),formData,
            data: formData,
            success: function (data) {
                console.log(data);
            },
            error: function (xhr) {
                alert('오류:' + xhr.status)
            }

        })
        return false





    })
})