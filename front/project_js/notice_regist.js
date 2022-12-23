$(function(){
    let $title = $('input[name=title]')
    let $noti = $('textarea[name=noti]')
    let id
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
    // -----작성 버튼 클릭 시작 -----
    $('foot>div>.bt').click(()=>{
        let notice = {}
        notice.notiTitle = $title.val()
        notice.notiDes = $noti.val()
        notice.notiId = id
        console.log(id)
        $.ajax({
            url: backURL+'notice/new',
            method: 'post',
            data: JSON.stringify(notice),
            headers: {'Content-Type': 'application/json'},
            success: function(jsonObj){
                alert(jsonObj.msg)
                location.href = '../project_html/notice.html'
            },
            error: function(){
                alert("작성 실패"+jsonObj.msg)
            }
        })
    })
    // -----작성 버튼 클릭 끝 -----


})