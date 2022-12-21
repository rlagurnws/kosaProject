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
    let $title = $('input[name=title]')
    let $noti = $('textarea[name=noti]')

    // -----작성 버튼 클릭 시작 -----
    $('foot>div>.bt').click(()=>{
        alert('title='+$title.val()+'&noti='+$noti.val())
        $.ajax({
            url: 'http://192.168.2.22:8888/kosaproject/addnoti',
            method: 'post',
            data: 'title='+$title.val()+'&noti='+$noti.val(),
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