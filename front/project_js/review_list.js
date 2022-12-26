$(function(){
    let $center = $('.center')
    let $side = $('.mySidenav')
    let $menu = $('span.menu')
    let $close = $('section>a.closebtn')

    $menu.click(()=>{      
        $side.width('20%')
        $center.css('marginLeft','20%')
    })
    $close.click(()=>{
        $side.width(0)
        $center.css('marginLeft',0)
    })

})