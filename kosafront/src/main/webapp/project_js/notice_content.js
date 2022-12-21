function openNav() {
    document.querySelector(".mySidenav").style.width = '20%';
    document.querySelector('div.center').style.marginLeft = '20%';
}
function closeNav() {
    document.querySelector(".mySidenav").style.width = '0';
    document.querySelector('div.center').style.marginLeft = '0';
}
$(function(){
    $.ajax({
        url: backURL+'session',
        success: function(jsonObj){
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
    let $logout = $('a.logout')
    $logout.click(()=>{
        $.ajax({
            url: backURL+'logout',
            success: function(){
                location.href=frontURL+'project_html/main.html'
            }
        })
    })
    
    let queryStr = location.search
    let arr = queryStr.substring(1).split('=')
    let notiNo = arr[1]
    let noti = arr[0]
    let tes = $('button.noti_post').html()
    // ----- 페이지 로드 시작 -----
    $.ajax({
        url : backURL+'noticontent',
        data : 'noti_no='+notiNo,
        success: function(jsonObj){
            if(jsonObj.status == 1){
                let noti = jsonObj.noti
                $('nav').html(noti.noti_title)
                $('div.noti_content').html(noti.noti_des)
            }else if(jsonObj.status == 0){
                alert(jsonObj.msg)
            }
        }
    })
    // ----- 페이지 로드 끝 -----

    // ----- 다음 글 버튼 클릭 시작 -----
    $("button.noti_post").click(()=>{
        $.ajax({
            url : backURL+'nextnoti',
            data : 'noti_no='+(notiNo),
            success: function(jsonObj){
                if(jsonObj.status == 1){
					notiNo = jsonObj.nextNo
                    location.href = '../project_html/notice_content.html?noti_no='+notiNo
                }else if(jsonObj.status == 0){
                    alert(jsonObj.msg)
                }
            }
        })
    })
    // ----- 다음 글 버튼 클릭 끝 -----
	
	// ----- 이전 글 버튼 클릭 시작 -----
	$("button.noti_pre").click(()=>{
        $.ajax({
            url : backURL+'prenoti',
            data : 'noti_no='+(notiNo),
            success: function(jsonObj){
                if(jsonObj.status == 1){
					notiNo = jsonObj.preNo
                    location.href = '../project_html/notice_content.html?noti_no='+notiNo
                }else if(jsonObj.status == 0){
                    alert(jsonObj.msg)
                }
            }
        })
    })
	// ----- 이전 글 버튼 클릭 끝 -----

})

