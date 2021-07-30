(function($){
	$(document).ready(function() {

		ClassicEditor
        .create( document.querySelector( '#editor' ))
        .then( editor => {
        	if($(".post_content")[0]){
        		editor.setData($(".post_content").html());
        		$(".post_content").remove();
        	}
        	editor.isReadOnly = true;
        } )
        .catch( error => {
            console.error( error );
        } );

		$("#attachedFileListOpen").on("click", function() {
			$(".post_attached_files-list").toggle();
		});

		var getFileName = function(contentDisposition) {
		    var fileName = contentDisposition
		        .split(';')
		        .filter(function(ele) {
		            return ele.indexOf('filename') > -1
		        })
		        .map(function(ele) {
		            return ele
		                .replace(/"/g, '')
		                .split('=')[1]
		        });
		    return fileName[0] ? fileName[0] : null
		}

		var getProfileImage = function(parent,userId){
			if(userId == "비공개"){
				userId = null;
			}
			$.ajax({
				url: "/util/profile",
				xhr:function(){
							var xhr = new XMLHttpRequest();
							xhr.onloadstart = function() {
							    xhr.responseType = "blob";
							}
				            return xhr;
			     		},
				type:"get",
				data:"uid="+userId,
				success: function(data, textStatus, jqXhr) {
			        if (!data) {
			            return;
			        }
			        try {
			        	var fileName = getFileName(jqXhr.getResponseHeader('content-disposition'));
				        fileName = decodeURI(fileName);

				        var extension = fileName.substr(fileName.lastIndexOf(".")+1);
				        var content_type;

				        if(extension == "svg"){
				        	content_type = "image/svg+xml";
				        }
				        else if(extension == "png"){
				        	content_type = "image/png";
				        }
				        else if(extension == "bmp"){
				        	content_type = "image/bmp";
				        }
				        else{
				        	content_type = "image/jpeg";
				        }

				        var data = new Blob([data], {
							  type: content_type
							});

			            var url = window.URL.createObjectURL(data);

			            var img = $("<img/>").attr("class","author_img").attr("src",url);
			            parent.append(img);

			        } catch (e) {
			            console.error(e)
			        }
				}
			});
		}


		$(".attached_file_download").on("click", function() {
			var ofn = $(this).parent().children("span").text();
			var sfn = $(this).attr("id");
			var pid = $(".post_info").attr("id");
			var author = $("#author").text();
			var uid = (author != "비공개" ? author : null);

			var fdata = {"ofn" : ofn,"sfn" : sfn, "pid":pid , "uid":uid};

			$.ajax({
				url: "/util/download",
				xhr:function(){
							var xhr = new XMLHttpRequest();
							xhr.onloadstart = function() {
							    xhr.responseType = "blob";
							}
				            return xhr;
			     		},
				type:"post",
				data:JSON.stringify(fdata),
				contentType: "application/json",
				success: function(data, textStatus, jqXhr) {
			        if (!data) {
			            return;
			        }
			        try {
			            var fileName = getFileName(jqXhr.getResponseHeader('content-disposition'));
			            fileName = decodeURI(fileName);

			            if (window.navigator.msSaveOrOpenBlob) {
			                window.navigator.msSaveOrOpenBlob(data, fileName);
			            } else {
			                var url = window.URL.createObjectURL(data);

			                var link = $("<a>")
			                .attr("href", url)
			                .attr("download", fileName)
			                .appendTo("body");

			            	link[0].click();
			            	link.remove();

			                window.URL.revokeObjectURL(url);
			            }
			        } catch (e) {
			            console.error(e)
			        }
				}
			});
		});

		getProfileImage($(".author_img_wrap"),$("#author").text());

	});
})(jQuery);