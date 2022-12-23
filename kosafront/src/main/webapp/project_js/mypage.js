function openNav() {
    document.querySelector(".mySidenav").style.width = '20%';
    document.querySelector('div.center').style.marginLeft = '20%';

}
function closeNav() {
    document.querySelector(".mySidenav").style.width = '0';
    document.querySelector('div.center').style.marginLeft = '0';
}
$(function(){
<<<<<<< HEAD
    let memId
    let memPower
=======
>>>>>>> origin/develop
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
<<<<<<< HEAD
        url: backURL+'member/session',
=======
        url: backURL+'session',
>>>>>>> origin/develop
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
<<<<<<< HEAD
                memId = jsonObj.id
                memPower = jsonObj.power
                info(memId)
=======
>>>>>>> origin/develop
                $('span.nickname').html(nick+'님 <br>안녕하세요')
            }else if(jsonObj.status==0){
                $('a.logout').hide()
				$('a.mypage').hide()
				$('span.nickname').hide()
            }
        }
    })
<<<<<<< HEAD
=======
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

>>>>>>> origin/develop
    
    let $id = $('input[name=memId]')
    let $name = $('input[name=memName]')
    let $nick = $('input[name=memNick]')
    let $Bd = $('input[name=memBirth]')
<<<<<<< HEAD
    let $img = $('img.img')

    //----- 정보 받아오기 시작 -----
    function info(memId){
        $.ajax({
           xhrFields: {
               withCredentials: true
           },
           method: 'post',
           url: backURL+'member/mypage/'+memId,
           success: function(jsonObj){
                let profile = jsonObj.profile
                let m = jsonObj.member
                $id.val(m.memId)
                $name.val(m.memName)
                $nick.val(m.memNick)
                $Bd.val(m.memBirth)
                $img.attr('src','../project_image/profile/'+profile)
           }
       })
    }
    //----- 정보 받아오기 끝 -----

    //----- 이미지 변경 시작 -----
    function setThumbnail(event) {
        var reader = new FileReader();
        reader.onload = function(event) {
          $img.attr('src',event.target.result)
        }
        reader.readAsDataURL(event.target.files[0]);
      }

    $('input[name=img]').change((event)=>{
        setThumbnail(event)
    })
    //----- 이미지 변경 끝 -----




=======
    let $pic = $('file[name=img]')
    //----- 정보 받아오기 시작 -----
 	$.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL+'mypage',
        success: function(jsonObj){
            let m = jsonObj.member
            console.log(m)
            $id.val(m.memId)
            $name.val(m.memName)
            $nick.val(m.memNick)
            $Bd.val(m.memBirth)
            $pic.val(m.memPic)
		}
    })

    //----- 정보 받아오기 끝 -----

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
>>>>>>> origin/develop


    //--수정버튼 클릭이벤트 START--
    $('input[name=submit]').click(()=>{
        //비밀번호 유효성 검사
        let $pwd = $('input[name=memPwd]')
        let $pwd1 = $('input[id=pwd2]')

        if($pwd.val() != $pwd1.val()){
            alert("비밀번호가 서로다름")
            $pwd.focus()
            $pwd.select()
            return false
        }

<<<<<<< HEAD
        let $form = $('form')
        let formData = new FormData($form[0])

        let memId = $('input[name=memId]').val()
        let memPwd= $('input[name=memPwd]').val()
        let memName= $('input[name=memName]').val()
        let memPhone= $('input[name=memPhone]').val()
        let memSex= $('input[name=memSex]').val()
        let memNick= $('input[name=memNick]').val()
        let memBirth= $('input[name=memBirth]').val()

        formData.append('memPower',memPower)
        formData.append('memId', memId)
        formData.append('memPwd', memPwd)
        formData.append('memName', memName)
        formData.append('memPhone', memPhone)
        formData.append('memSex', memSex)
        formData.append('memNick', memNick)
        formData.append('memBirth', memBirth)
=======


        let url = backURL+'memmodi'
        let method = 'post'
        let data = $('form').serialize()
        console.log(data)
>>>>>>> origin/develop
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
<<<<<<< HEAD
            processData : false,
            contentType : false,
            url : backURL+'member/modify',
            method : 'post',
            data : formData,
            success : function(jsonObj){
                if(jsonObj.status == 1){
                    alert(jsonObj.msg)
                    location= "main.html"
=======
            url : url,
            method : method,
            data : data,
            success : function(jsonObj){
                if(jsonObj.status == 1){
                    alert(jsonObj.msg)
                    location= frontURL+"project_html/main.html"
>>>>>>> origin/develop
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
    //--수정버튼 클릭이벤트 END--

    //-----회원 탈퇴 이벤트 시작 -----

    $('input[type=button]').click(()=>{
<<<<<<< HEAD
        console.log(memId)
=======
>>>>>>> origin/develop
        if(confirm('진짜 탈퇴할거에요?')){
			$.ajax({
                xhrFields: {
                    withCredentials: true
                },
<<<<<<< HEAD
                method: 'put',
                url : backURL+'member/'+memId,
=======
                url : backURL+'delmem',
>>>>>>> origin/develop
                success: function(jsonObj){
                    if(jsonObj.status == 1){
                        alert("회원 탈퇴!")
                        location.href='main.html'
                    }else{
                        alert("에러")
                    }
                }
            })
		}else{
		}

    })
    //-----회원 탈퇴 이벤트 끝 -----

})