$(function (){
    let str = location.search.substring(1)
    let code = str.split('=')
    let data = {}
    data.grant_type = 'authorization_code'
    data.client_id = 'c25a5bde966a7f0056b5743578d222dc'
    data.redirect_uri = 'http://192.168.2.22:5500/kosafront/src/main/webapp/project_html/kakao.html'
    data.code = code[1]
    console.log(data)
    $.ajax({
        url: 'https://kauth.kakao.com/oauth/token',
        method: 'post',
        data: data,
        success: function(jsonObj){
            let idToken = jsonObj.id_token.split('.') //id_token 페이로드 값 (base64)
            console.log(idToken[1])
            console.log(jsonObj.access_token)
            let test = decodeURIComponent(escape(window.atob(idToken[1])));
            console.log(test)
            let testObj = JSON.parse(test)
            let realnick = testObj.nickname
            let id = testObj.sub
            // let obj = test.split(',')
            // let id2 = obj[1].split(':')
            // let id = id2[1]
            // let nick = obj[4].split(':')
            // let realnick = nick[1]
            // console.log(realnick)
            // console.log(id)
            // kakaoLogin(id, realnick)
        }
    })
})

function kakaoLogin(id, nickname){
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL+'member/mypage/'+id,
        method: 'post',
        success: function(jsonObj){
            if(jsonObj.status==0){
                alert("회원 가입 페이지로 이동합니다.")
                location.href = '../project_html/signup.html?id='+id+'&nickname='+nickname
            }else{
                alert("로그인 되었습니다.")
                login(jsonObj.member)
            }
        }
    })

}

function login(member){
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL+'member/'+member.memId,
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
}