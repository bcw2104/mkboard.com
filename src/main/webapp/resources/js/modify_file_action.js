(function($){

	var fileSizeCheck = function(fileSize){
		var maxSize = 3 * 1024 * 1024;

		if(fileSize > maxSize){
			return false;
		}
		else{
			return true;
		}
	}

	$(document).ready(function() {

		var fileUploadTemp = function(target){
			var fileSize = target.files[0].size;
			var fileExtension = target.files[0].name.substr(target.files[0].name.lastIndexOf(".")+1);

			if(fileExtension == "svg" || fileExtension == "bmp" || fileExtension == "png" || fileExtension=="jpg"){
				if(fileSizeCheck(fileSize)){
					var fReader = new FileReader();
					fReader.readAsDataURL(target.files[0]);
					fReader.onloadend = function(event){
					    $("#profileImg").attr("src",event.target.result);
					    $(".selected_upload_file").remove();
					    $(".upload_file").removeClass("upload_file").addClass("selected_upload_file").attr("name", "upload_file");
					}
				}else{
					$(".upload_file").remove();
					alert("파일 크기가 3MB를 초과합니다.");
				}
			}else{
				$(".upload_file").remove();
				alert("프로필 사진의 확장자는 svg,bmp,png,jpg만 가능합니다.");
			}
		}

		$("#changeImageBtn").click(function() {

			var agent = navigator.userAgent.toLowerCase();
			var findFile = $("<input/>").attr("type","file")
														.addClass("upload_file")
														.attr("hidden", "hidden");

			$(this).after(findFile);

			//IE
			if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
				findFile.on("click",function(event) {
					var target = event.target;
					setTimeout(function(){
						if($(target).val().length > 0){
							fileUploadTemp(target);
						}
					 }, 0);
				});
			}
			else{
				findFile.on("change", function(event) {
					var target = event.target;
					if($(target).val().length > 0){
						fileUploadTemp(target);
					}
				});
			}

			findFile[0].click();

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


		var getProfileImage = function(){
			var userId = $("#userId").val();
			$.ajax({
				url: "/util/profile",
				xhr:function(){
							var xhr = new XMLHttpRequest();
							xhr.onloadstart = function() {
							    xhr.responseType = "blob";
							}
				            return xhr;
			     		},
			    data:"uid="+userId,
				type:"get",
				success: function(data, textStatus, jqXhr) {
			        if (!data) {
			            return;
			        }
			        try {
			        	var fileName = getFileName(jqXhr.getResponseHeader('content-disposition'));
				        fileName = decodeURI(fileName);

				        var extension = fileName.substr(fileName.lastIndexOf("."));
				        var content_type;

				        if(extension == ".svg"){
				        	content_type = "image/svg+xml";
				        }
				        else if(extension == ".png"){
				        	content_type = "image/png";
				        }
				        else{
				        	content_type = "image/jpeg";
				        }

				        var data = new Blob([data], {
							  type: content_type
							});

			            var url = window.URL.createObjectURL(data);

			            var img = $("<img/>").attr("id","profileImg").attr("class","profile_img").attr("src",url);
			            $(".profile_img_wrap").append(img);

			        } catch (e) {
			            console.error(e)
			        }
				}
			});
		}

		getProfileImage();
	});
})(jQuery);