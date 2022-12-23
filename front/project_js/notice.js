$(function(){
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL+'member/session',
        success: function(jsonObj){
            console.log(jsonObj.power)
            if(jsonObj.power!=0){
                $('button.write').hide()
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
    let $content = $('tbody.noti')
    function showList(currentPage){
		let el = $('tbody.noti').not($content)
		el.remove();
        $.ajax({
        xhrFields: {
            withCredentials: true
        },
            url : backURL +'notice/list/'+currentPage,
            success: function(jsonObj){
                if(jsonObj.status == 1){
                    let $noti = $('tbody.noti') //원본
                    $noti.show()
                    let $divtb = $('table')
                    let notis = jsonObj.pb.list
                    $(notis).each(function(index,n){
                        console.log(n.noti_no)
                        let $copynoti = $noti.clone() //복제본
                        $copynoti.find('td.no').html(n.notiNo)
                        $copynoti.find('td.ti').html(n.notiTitle)
                        $copynoti.find('td.da').html(n.notiDate)
                        $divtb.append($copynoti)
                    })
                }
                $content.hide();
                
                //페이지 목록 만들기
                let startPage = jsonObj.pb.startPage //페이지목록그룹시작페이지
                let endPage = jsonObj.pb.endPage //페이지목록그룹끝페이지
                let liStr = '';
                if(startPage>1){
                    liStr += '<li class= "'+(startPage-1)+'">PREV'+'&nbsp'+'</li>'
                }
                for(let i=startPage; i<=endPage; i++){
                    liStr += "<li class='"+i+"'>"+i+"</li>"
                }
                let totalPage = jsonObj.pb.totalPage;
                if(totalPage > endPage){
                    liStr += '<li class= "'+(endPage+1)+'">'+'&nbsp'+'NEXT</li>'
                }
                $('div.pages>ul').html(liStr)
            }
        })
    }
    showList(1)
    //-----페이지 클릭이벤트 시작-----
    let $lipage = $('div.pages li')
    $('div.pages>ul').on('click','li',(e)=>{
        let currentPage = $(e.target).attr('class')
        showList(currentPage)
    })
    //-----페이지 클릭이벤트 끝-----

    //----- 공지사항 클릭 이벤트 시작 -----
    $('table').on('click','.noti',(e)=>{
        let $noti = $(e.target).parents('tr')
        let notiNo = $noti.find('td.no').html()
        location.href = '../project_html/notice_content.html?'+notiNo
    })
    //----- 공지사항 클릭 이벤트 끝 -----

    //----- 공지사항 작성 이벤트 시작 -----
    $('button.write').click(()=>{
        location.href ='notice_regist.html'
    })
    //----- 공지사항 작성 이벤트 끝 -----
})