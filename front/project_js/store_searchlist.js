function openNav() {
    document.querySelector(".mySidenav").style.width = '20%'
    document.querySelector('div.center').style.marginLeft = '20%'
}
function closeNav() {
    document.querySelector(".mySidenav").style.width = '0'
    document.querySelector('div.center').style.marginLeft = '0'
}


$(function () {
   
    //url값 받아오기
    // $.urlParam = function(name){
    //     var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    //     return results[1] || 0;
    // }
    // let $searchVal  =$.urlParam('search')

    // console.log($.urlParam('search'))
    let test = location.search.substring(1)
    let search = decodeURI(test.split('=')[1])
    
    let $content = $('div.store_lists')

    function showStore(currentPage){
        let el = $('div.store_lists').not($content)
        el.remove();

        let searchlist ={}
        searchlist.currentPage = currentPage
        searchlist.search = search

        
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            method : "post",
            url : backURL +'store/storelist/' +currentPage,
            data : searchlist,
            success: function (jsonObj) {
                let $storeList = $('div.store_list')//원본
                
                $storeList.show()
                let $divsl = $('div.store_lists')
                let stol = jsonObj.list
                $(stol).each(function(index,n){
                    console.log(n.cateNum)
                    console.log(n.stHits)
                    console.log(n.stName)
                    console.log(n.stLoca)
                    console.log(n.stNum)
                    let $copynoti = $storeList.clone()
                    $copynoti.find('div.store_list__tag').html(n.cateNum)
                    $copynoti.find('div.store_list__tag2').html(n.stNum)
                    $copynoti.find('div.store_list__view').html(n.stHits)
                    //$copynoti.find('img').attr('src', 'C:/files'+) //이미지처리 어케하지
                    $copynoti.find('div.store_list__textBox__name').html(n.stName)
                    $copynoti.find('div.store_list__textBox__sub').html(n.stLoca)
                    $divsl.append($copynoti)
                })

                $storeList.hide();
                
                //페이지 목록 만들기
                let startPage = jsonObj.startPage //페이지목록그룹시작페이지
                let endPage = jsonObj.endPage //페이지목록그룹끝페이지
                let liStr = '';
                if(startPage>1){
                    liStr += '<li class= "'+(startPage-1)+'">PREV'+'&nbsp'+'</li>'
                }
                for(let i=startPage; i<=endPage; i++){
                    liStr += "<li class='"+i+"'>"+i+"</li>"
                }
                let totalPage = jsonObj.totalPage;
                if(totalPage > endPage){
                    liStr += '<li class= "'+(endPage+1)+'">'+'&nbsp'+'NEXT</li>'
                }
                $('div.pages>ul').html(liStr)
            }
        })

    }
    showStore(1)


    $('div.store_lists').on('click','div.store_list',(e)=>{
        let storeNo = $(e.target).parent().parent().children('.store_list__tags').children('.store_list__tag2').html()
       
        location.href = '../project_html/storedetail.html?'+storeNo
    })
})
