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
            xhrFields: {
                withCredentials: true
            },
            url: backURL+'member/logout',
            success: function(){
                location.href= 'main.html'
            }
        })
    })
    $('button.login').click(()=>{
        let id = $('input[name=id]').val()
        let pwd = $('input[name=pwd]').val()
        let member = {}
        member.memId = id
        member.memPwd = pwd
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            url: backURL+'member/'+id,
            method : 'post',
            data: JSON.stringify(member),
            headers: {'Content-Type': 'application/json'},
            success: function(jsonObj){
                if(jsonObj.status == 1){
                    alert("로그인 성공")
                    location.href='main.html'
                }else if(jsonObj.status == 0){
                    alert(jsonObj.msg)
                }
            }
        })
    })

    $('button.signup').click(()=>{
        location.href='signup.html'
    })
})
