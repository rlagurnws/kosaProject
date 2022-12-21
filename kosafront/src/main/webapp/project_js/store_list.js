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
            url : backURL +'',
            data : 'currentPage='+currentPage,
            success: function (data) {
                let $storeList = $('div.store_lists')
                $storeList.show()
                let $divsl = $('div.store_list')
            },
            error: function (xhr) {
                alert('오류:' + xhr.status)
            }
        })

    }
    showStore(1)
})
