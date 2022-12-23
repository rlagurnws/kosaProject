$(function () {
    //--글자수 제한 
    $('.text_box textarea').keyup(function () {
        var content = $(this).val();

        $('.text_box .count span').html(content.length);
        if (content.length > 200) {
            alert("최대 200자까지 입력 가능합니다.");
            $(this).val(content.substring(0, 200));
            $('.text_box .count span').html(200);
        }
        return false
    });


    //----- 이미지 변경 시작 -----
    let $img = $('img.img')
    $img.hide()
    function setThumbnail(event) {
        var reader = new FileReader();
        reader.onload = function(event) {
          $img.attr('src',event.target.result)
          $('div.fileInput>p#fileName').html($('input[name=chooseFile]').val())
        }
        reader.readAsDataURL(event.target.files[0]);
      }

    $('input[name=chooseFile]').change((event)=>{
        setThumbnail(event)
    })
    $('div.buttonContainer').click(()=>{
        $img.show()
        $('div.fileContainer').hide()
    })
    //----- 이미지 변경 끝 -----
   
})

     
  
   






















