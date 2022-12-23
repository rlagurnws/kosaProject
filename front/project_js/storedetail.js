$(function(){
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL+'member/session',
        success: function(jsonObj){
            if(jsonObj.power!=0){
                $('button.delete').hide()
                //$('button.modify').hide()
                $('a.storeList').hide()
            }
            if(jsonObj.power==2){
                $('a.store').show()
            }else{
                $('a.store').hide()
            }
            if(jsonObj.status==1){
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
    let queryStr = location.search
    let StoreNo = queryStr.substring(1)
    let $nav = $('nav')

    let $detail2 = $('aside>div.content>div.detail2')
    let $detail = $('aside>div.content>div.detail2>div.detail')
    home()




    //----- 기본정보 불러오기 시작 -----
    function home(){
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            url : backURL + 'store/'+StoreNo,
            method : 'post',
            success: function(jsonObj){
                $detail.show()
                $el = $('aside>div.content>div.detail2>div.detail').not($detail)
                $el.remove()
                let store = jsonObj.store
                $copy = $detail.clone()
                $nav.html(store.stName)
                $copy.append('가게 이름      : '+store.stName +'<br>')
                $copy.append('설  명         : '+store.stDes+'<br>')
                $copy.append('등록 날짜      : '+store.stDate+'<br>')
                $copy.append('위  치        : '+store.stLoca+'<br>')
                $copy.append('전화번호       : '+store.stPhone+'<br>')
                $copy.append('사업자 등록번호 : '+store.stResNo+'<br>')
                $detail2.append($copy)
                if(store.stStatus == 1){
                    $('button.ok').hide()
                }
                $detail.hide()
            }
        })
    }
    //----- 기본정보 불러오기 끝 -----

    // ----- 메뉴 불러오기 시작 -----
    function menu(){
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            url : backURL + 'store/menu/'+StoreNo,
            method : 'post',
            success: function(jsonObj){
                $detail.show()
                $el = $('aside>div.content>div.detail2>div.detail').not($detail)
                $el.remove()
                let list = jsonObj.list
                let i = 0
                let menu = jsonObj.menuFile
                list.forEach(m => {
                    let $copy = $detail.clone()
                    $copy.append('<div class="menuImg"> </div> <div class="menuInfo"></div><br>')
                    $copy.find('div.menuImg').append('<img>')
                    $copy.find('img').attr('src', '../project_image/menu/'+menu[i])
                    $copy.find('div.menuInfo').append(m.menuName+'<br>')
                    $copy.find('div.menuInfo').append(m.menuPrice+'원<br>')
                    $detail2.append($copy)
                    i++
                });
                $detail.hide()
            }
        })
    }
    // ----- 메뉴 불러오기 끝 -----

    // ----- 지도 불러오기 시작 -----
    function loca(){
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            url : backURL + 'store/loca/'+StoreNo,
            method : 'post',
            success: function(jsonObj){
                $detail.show()
                $el = $('aside>div.content>div.detail2>div.detail').not($detail)
                $el.remove()
                let address = jsonObj.loca
                let name = jsonObj.name
                let $copy = $detail.clone()
                $copy.append('<div id="map"></div>')
                $detail2.append($copy)
                mapreview(address,name)
                $detail.hide()
            }
        })
    }


    function mapreview(loca, name) {
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
            mapOption = {
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };
        console.log(mapContainer)
        // 지도를 생성합니다    
        var map = new kakao.maps.Map(mapContainer, mapOption);
        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();
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
    // ----- 지도 불러오기 끝 -----



    // ----- 버튼 클릭 이벤트 시작 -----
    $('button.home').click(()=>{
        home()
    })
    $('button.menu').click(()=>{
        menu()
    })
    $('button.loca').click(()=>{
        loca()
    })
    // ----- 버튼 클릭 이벤트 끝 -----

    // ----- 승인 클릭 이벤트 시작 -----
    $('button.ok').click(()=>{
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            url: backURL+'store/'+StoreNo,
            method: 'put',
            success: function(jsonObj){
                if(jsonObj.status==1){
                    alert("승인 성공!")
                    location.href = '../project_html/store_list.html'
                }else{
                    alert("승인 실패")
                }
            }
        })
    })
    // ----- 승인 클릭 이벤트 끝 -----



    // -------수정 버튼 클릭 이벤트 시작
    $('button.modify').click(()=>{
        alert('수정버튼 클릭')
        let stNum = location.search.substring(1)
        //let search = decodeURI(test.split('=')[1])
        console.log(stNum)
        location.href = '../project_html/store_modify_regist.html?'+stNum
    })
})