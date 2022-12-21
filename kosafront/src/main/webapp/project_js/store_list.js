function openNav() {
    document.querySelector(".mySidenav").style.width = '20%'
    document.querySelector('div.center').style.marginLeft = '20%'
}
function closeNav() {
    document.querySelector(".mySidenav").style.width = '0'
    document.querySelector('div.center').style.marginLeft = '0'
}

$(function(){

    let $content = $('tbody.noti')
    function showList(currentPage){
		let el = $('tbody.noti').not($content)
		el.remove();
        $.ajax({
            url : backURL +'noticelist',
            data : 'currentPage='+currentPage,
            success: function(jsonObj){
                if(jsonObj.status == 1){
                    let $noti = $('tbody.noti') //원본
                    $noti.show()
                    let $divtb = $('table')
                    let notis = jsonObj.pb.list
                    $(notis).each(function(index,n){
                        console.log(n.noti_no,n.noti_title,n.noti_date)
                        let $copynoti = $noti.clone() //복제본
                        $copynoti.find('td.no').html(n.noti_no)
                        $copynoti.find('td.ti').html(n.noti_title)
                        $copynoti.find('td.da').html(n.noti_date)
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

    //DOM트리에 생성되지 않은 객체이벤트 처리는 on함수로 실행
    // $lipage.click((e)=>{
    $('div.pages>ul').on('click','li',(e)=>{
        let currentPage = $(e.target).attr('class')
        console.log($(e.target).html())
        showList(currentPage)
    })
    //-----페이지 클릭이벤트 끝-----

    //----- 공지사항 클릭 이벤트 시작 -----
    $('tbody.noti').on('click','.tr',(e)=>{
		console.log("여까진오케이")
        let $noti = $(e.target).parents('tr')
        let notiNo = $noti.find('td.no').html()
        location.href = '../html/notice_content.html?noti_no='+notiNo
    })
    //----- 공지사항 클릭 이벤트 끝 -----
})