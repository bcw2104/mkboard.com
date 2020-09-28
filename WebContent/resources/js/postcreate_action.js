
(function($){

	$(document).ready(function() {
		ClassicEditor
        .create( document.querySelector( '#editor' ), {
        	toolbar: [ 'heading', '|', 'bold', 'italic','underline','strikethrough','|',
        			'fontSize','fontColor', 'fontBackgroundColor','|',
        		'bulletedList', 'numberedList','|', 'link', 'blockQuote','undo', 'redo' ],
        	heading: { options: [ { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
				    		{ model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
				    		{ model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
				    		{ model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
				    		{ model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' } ] },
			placeholder: '글을 작성해주세요.'

        })
        .then( editor => {
        	if($("#preContent")){
        		editor.setData($("#preContent").html());
        		$("#preContent").remove();
        	}
        } )
        .catch( error => {
            console.error( error );
        } );


		$("#postSubmitBtn").on("click", function(event) {

			if($(".ck-editor__editable").html() == ""){
				alert("내용을 작성해주세요.");

				return false;
			}
			else{
				$("#postForm").append("<input type='hidden' name='post_content' value='"+$(".ck-editor__editable").html()+"'>");
				return true;
			}
		});
	});

})(jQuery);