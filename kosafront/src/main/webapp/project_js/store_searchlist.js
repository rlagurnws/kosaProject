function openNav() {
    document.querySelector(".mySidenav").style.width = '20%'
    document.querySelector('div.center').style.marginLeft = '20%'
}
function closeNav() {
    document.querySelector(".mySidenav").style.width = '0'
    document.querySelector('div.center').style.marginLeft = '0'
}


$(function () {


    let $content = $('div.store_lists')

    function showStore(currentPage){
        let el = $('div.store_lists').not($content)
        el.remove();



        
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            method : get,
            url : 
            frontURLRL +'store/storelist',
            data : 'currentPage='+currentPage,
            success: function (jsonObj) {
                let $storeList = $('div.store_lists')//원본
                $storeList.show()
                let $divsl = $('div.store_list')
                let notis = jsonObj.pb.list
                $(notis).each(function(index,n){
                    let $copynoti = $noti.clone()
                    $copynoti.find('div.store_list__tag').html(n.cateNum)
                    $copynoti.find('div.store_list__view').html(n.stHits)
                    $copynoti.find('img').atte("src:"+ ) //이미지처리 어케하지
                    $copynoti.find('div.store_list__textBox__name').html(n.stName)
                    $copynoti.find('div.store_list__textBox__sub').html(n.stLoca)
                    $divsl.append($copynoti)
                })

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
    showStore(1)
})
