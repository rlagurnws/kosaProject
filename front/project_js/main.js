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
            if(jsonObj.power!=0){
                $('a.storeList').hide()
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
                location.href=frontURL+'project_html/main.html'
            }
        })
    })

   
    $('input.searchbt').click(() => {
        let $search = $('input[name=search]')
        alert("검색성공!")
        location.href = frontURL+'project_html/store_list2.html?search='+$search.val()
        
    })
   
})