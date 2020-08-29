(function($){
	$(document).ready(function() {

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


		var getProfileImage = function(){
			var userId = $("#userId").text();
			userId = userId.substring(userId.indexOf("(")+1, userId.lastIndexOf(")"));
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

			            var img = $("<img/>").attr("class","profile_img").attr("src",url);
			            $(".profile_img_wrap").append(img);

			        } catch (e) {
			            console.error(e)
			        }
				},
			    error:function(request,status,error){
			        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			       }
			});
		}

		getProfileImage();
	});
})(jQuery);