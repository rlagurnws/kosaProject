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
            xhrFields: {
                withCredentials: true
            },
            url: backURL+'logout',
            success: function(){
                location.href=frontURL+'project_html/main.html'
            }
        })
    })


    
    let $id = $('input[name=id]')
    $('input[name=submit]').hide()

    //--아이디중복확인버튼 클릭이벤트 START--
    let $btIdDupChk = $('button.iddupchk')
    $btIdDupChk.click(()=>{
        let url = backURL+'iddupchk'
        let method = 'post'
        let data = 'id='+$id.val()
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            url:url,
            method:method,
            data: data,
            success: function(jsonObj){
                if(jsonObj.status == 0){
                    alert("아이디 사용 불가")
                    $('input[name=submit]').hide()
                }else if(jsonObj.status == 1){
                    alert("아이디 사용 가능")
                    $('input[name=submit]').show()
                }
            },
            error: function(xhr){
                alert("error:" + xhr.status)
            }
        })
    })
    //--아이디중복확인버튼 클릭이벤트 END--

    //--아이디입력란 포커스이벤트 START--
    //$id.focus(()=>{
    //    $('input[name=submit]').hide()
    //})
    //--아이디입력란 포커스이벤트 END--

    //-----지역 선택 이벤트 시작-----
    let $selectloca = $('select.locaType')
    let $region = $('select.region')
    let $copy = $('select.region>.copy')
    $selectloca.change(()=>{
        $copy.show()
        let region = $selectloca.val()
        let el = $('.region>.copy').not($copy)
        el.remove()
        switch(region){
            case 'gg':
                $region.append($copy.clone().html('수원시'))
                $region.append($copy.clone().html('의왕시'))
                $region.append($copy.clone().html('안양시'))
                $copy.hide()
                break
            case 'jr':
                $region.append($copy.clone().html('고흥군'))
                $region.append($copy.clone().html('광주시'))
                $region.append($copy.clone().html('등등등'))
                $copy.hide()
        }
    })
    //-----지역 선택 이벤트 끝 -----


    //--가입버튼 클릭이벤트 START--
    $('input[name=submit]').click(()=>{
        //비밀번호 유효성 검사
        let $pwd = $('input[name=memPwd]')
        let $pwd1 = $('input[id=pwd2]')

        if($pwd.val() != $pwd1.val()){
            alert("비밀번호가 서로다름")
            $pwd.focus();
            $pwd.select()
            return false
        }



        let url = backURL+'signup'
        let method = 'post'
        let data = $('form').serialize()
        console.log('serialize결과', data)
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            url : url,
            method : method,
            data : data,
            success : function(jsonObj){
                if(jsonObj.status == 1){
                    alert(jsonObj.msg)
                    location= frontURL+"project_html/main.html"
                }else if(jsonObj.status == 0){
                    alert(jsonObj.msg)
                }
            },
            error : function(xhr){
                alert("error :" + xhr.status)
            }

        })
        return false;
    })
    //--가입버튼 클릭이벤트 END--
})