function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,questionId, 1, content);

}

function comment2target(targetId,targetId2, type, content) {
    if (!content) {
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "parentId2": targetId2,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var commentId2=e.getAttribute("data-id2");
    var content = $("#input-" + commentId).val();
    comment2target(commentId,commentId2, 2, content);
}

function comment3Target(e) {
    var id=e.getAttribute("data-id");
    var commentName=e.getAttribute("comment-name");
    var id2=e.getAttribute("data-id2");
    var button=$("#input-button-" + id);
    var input=$("#input-"+id);
    button.attr("data-id2",id2);
    input.attr("placeholder","@"+commentName);
}

function likeCountShow(e){
    var id=e.getAttribute("data-id");
    var collapse=e.getAttribute("data-collapse");
    var likeNumber = $("#likeNumber-" + id).text();
    var likeCount=$("#likeNumber-" + id);
    var status=0;
    var user=e.getAttribute("user");
    if(!user){
        alert("请登录");
        return;
    }
    if(collapse){
        likeNumber--;
        likeCount.text(likeNumber);
        e.classList.remove("active");
        e.removeAttribute("data-collapse");
        status=0;
    }else{
        likeNumber++;
        likeCount.text(likeNumber);
        e.classList.add("active");
        e.setAttribute("data-collapse", "in");
        status=1;
    }
    $.ajax({
        type: "POST",
        url: "/comment/likeComment",
        contentType: "application/json",
        data: JSON.stringify({
            "likeCount": likeNumber,
            "id": id,
            "likeStatus": status
        }),
        success: function (response) {
            if (response.code == 200) {
                console.log(response);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function dislikeComment(e) {
    var id=e.getAttribute("data-id");
    var collapse=e.getAttribute("data-collapse");
    var dislikeNumber = $("#dislikeNumber-" + id).text();
    var dislikeCount=$("#dislikeNumber-" + id);
    var status=0;
    var user=e.getAttribute("user");
    if(!user){
        alert("请登录");
        return;
    }
    if(collapse){
        dislikeNumber--;
        dislikeCount.text(dislikeNumber);
        e.classList.remove("active");
        e.removeAttribute("data-collapse");
        status=0;
    }else{
        dislikeNumber++;
        dislikeCount.text(dislikeNumber);
        e.classList.add("active");
        e.setAttribute("data-collapse", "in");
        status=1;
    }
    $.ajax({
        type: "POST",
        url: "/comment/dislikeComment",
        contentType: "application/json",
        data: JSON.stringify({
            "dislikeCount": dislikeNumber,
            "id": id,
            "dislikeStatus": status
        }),
        success: function (response) {
            if (response.code == 200) {
                console.log(response);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var input = $("#input-" + id);
    var button=$("#input-button-" + id);
    button.attr("data-id2",id);
    input.attr("placeholder","请输入");

    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length !== 1) {
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data, function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    }).append($("<h4/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }).append($("<a/>",{
                        "onclick":"comment3Target(this)",
                        "html":"回复&nbsp&nbsp",
                        "class":"pull-left",
                        "data-id":comment.parentId,
                        "data-id2":comment.id,
                        "comment-name":comment.user.name
                    }))));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);


                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-t",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }

    }
}
function selectTag(value) {
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag() {
    $("#select-tag").show();
}

function deleteComment(e) {
    var commentId=e.getAttribute("comment-id");
    var parentId=e.getAttribute("parent-id");
    $.ajax({
        type: "POST",
        url: "/comment/deleteComment",
        contentType: "application/json",
        data: JSON.stringify({
            "id": commentId,
            "parentId":parentId
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                console.log(response);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function topComment(e) {
    var commentId=e.getAttribute("comment-id");
    $.ajax({
        type: "POST",
        url: "/comment/topComment",
        contentType: "application/json",
        data: JSON.stringify({
            "id": commentId
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                console.log(response);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function cancelTopComment(e) {
    var commentId=e.getAttribute("comment-id");
    $.ajax({
        type: "POST",
        url: "/comment/cancelTopComment",
        contentType: "application/json",
        data: JSON.stringify({
            "id": commentId
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                console.log(response);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function collectQuestion(e) {
    var id=e.getAttribute("data-id");
    var collapse=e.getAttribute("data-collapse");
    var collectNumber = $("#collectNumber-" + id).text();
    var collectCount=$("#collectNumber-" + id);
    var status=0;
    var user=e.getAttribute("user");
    if(!user){
        alert("请登录");
        return;
    }
    if(collapse){
        collectNumber--;
        collectCount.text(collectNumber);
        e.classList.remove("icon-star-filled");
        e.classList.add("icon-shoucang");
        e.removeAttribute("data-collapse");
        status=0;
    }else{
        collectNumber++;
        collectCount.text(collectNumber);
        e.classList.remove("icon-shoucang");
        e.classList.add("icon-star-filled");
        e.setAttribute("data-collapse", "in");
        status=1;
    }
    $.ajax({
        type: "POST",
        url: "/question/collectQuestion",
        contentType: "application/json",
        data: JSON.stringify({
            "collectCount": collectNumber,
            "id": id,
            "collectStatus": status
        }),
        success: function (response) {
            if (response.code == 200) {
                console.log(response);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function likeQuestion(e) {
    var id=e.getAttribute("data-id");
    var collapse=e.getAttribute("data-collapse");
    var likeNumber = $("#likeNumber-" + id).text();
    var likeCount=$("#likeNumber-" + id);
    var status=0;
    var user=e.getAttribute("user");
    if(!user){
        alert("请登录");
        return;
    }
    if(collapse){
        likeNumber--;
        likeCount.text(likeNumber);
        e.classList.remove("icon-dianzan");
        e.classList.add("icon-zan");
        e.removeAttribute("data-collapse");
        status=0;
    }else{
        likeNumber++;
        likeCount.text(likeNumber);
        e.classList.remove("icon-zan");
        e.classList.add("icon-dianzan");
        e.setAttribute("data-collapse", "in");
        status=1;
    }
    $.ajax({
        type: "POST",
        url: "/question/likeQuestion",
        contentType: "application/json",
        data: JSON.stringify({
            "likeCount": likeNumber,
            "id": id,
            "likeStatus": status
        }),
        success: function (response) {
            if (response.code == 200) {
                console.log(response);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

var countdown = 60;

function setTime(obj) {
    if (countdown == 0) {
        obj.removeAttribute("disabled");
        obj.value = "获取验证码";
        countdown = 60;
        return;
    } else {
        obj.setAttribute("disabled", true);
        obj.value = "重新发送(" + countdown + ")";
        countdown--;
    }
    setTimeout(function () {
        setTime(obj)
    },1000)
}
function setEmail(obj) {
var email=$("#email1").val();
if (obj.getAttribute("name") == "sm1") {
    email=$("#email2").val();
}
if (!email){
    alert("请输入邮箱");
    return;
}
    $.ajax({
        type: "POST",
        url: "/register/sendEmail",
        contentType: "application/json",
        data: JSON.stringify({
            "email": email
        }),
        success: function (response) {
            if (response.code == 200) {
                setTime(obj);
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function login() {
    var email=$("#email").val();
    var emailDiv=document.getElementById("email-div");
    var emailMsg=document.getElementById("email-msg");
    var pswDiv=document.getElementById("psw-div");
    var pswMsg=document.getElementById("psw-msg");
    if (!email) {
        alert("请输入邮箱");
        return;
    }
    var password=$("#password").val();
    if (!password) {
        alert("请输入密码");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/login",
        contentType: "application/json",
        data: JSON.stringify({
            "email":email,
            "password":password
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                console.log(response);
            } else if (response.code == 201) {
                emailDiv.classList.add("has-error");
                emailMsg.classList.remove("sr-only");
                pswDiv.classList.remove("has-error");
                pswMsg.classList.add("sr-only");
            }else if (response.code == 202) {
                emailDiv.classList.remove("has-error");
                emailMsg.classList.add("sr-only");
                pswDiv.classList.add("has-error");
                pswMsg.classList.remove("sr-only");
            }else{
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function password() {
    var email=$("#email1").val();
    var psw=$("#password1").val();
    var verifyCode=$("#idCode").val();
    var emailDiv=document.getElementById("email1-div");
    var emailMsg=document.getElementById("email1-msg");
    var pswDiv=document.getElementById("psw1-div");
    var pswMsg=document.getElementById("psw1-msg");
    var idcodeDiv=document.getElementById("idcode-div");
    var idcodeMsg=document.getElementById("idcode-msg");
    var idcodeMsg1=document.getElementById("idcode-msg1");
    if (!email) {
        alert("请输入邮箱");
        return;
    }
    if (!psw) {
        alert("请输入密码");
        return;
    }
    if (psw.length<6||psw.length>18){
        pswDiv.classList.add("has-error");
        pswMsg.classList.remove("sr-only");
    }

    $.ajax({
        type: "POST",
        url: "/login/password",
        contentType: "application/json",
        data: JSON.stringify({
            "email":email,
            "password":psw,
            "verifyCode":verifyCode
        }),
        success: function (response) {
            if (response.code == 200) {
                alert("修改成功，请重新登录");
                console.log(response);
            } else if (response.code == 203) {
                emailDiv.classList.add("has-error");
                emailMsg.classList.remove("sr-only");
                idcodeDiv.classList.remove("has-error");
                idcodeMsg.classList.add("sr-only");
                idcodeMsg1.classList.add("sr-only");
            }else if (response.code == 202) {
                idcodeDiv.classList.add("has-error");
                idcodeMsg.classList.remove("sr-only");
                idcodeMsg1.classList.add("sr-only");
                emailDiv.classList.remove("has-error");
                emailMsg.classList.add("sr-only");
            }else if (response.code == 201) {
                idcodeDiv.classList.add("has-error");
                idcodeMsg1.classList.remove("sr-only");
                idcodeMsg.classList.add("sr-only");
                emailDiv.classList.remove("has-error");
                emailMsg.classList.add("sr-only");
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function uploadFile() {
    var emailDiv=document.getElementById("email2-div");
    var emailMsg1=document.getElementById("email2-msg1");
    var emailMsg2=document.getElementById("email2-msg2");
    var usernameDiv=document.getElementById("username-div");
    var usernameMsg=document.getElementById("username-msg");
    var pswDiv=document.getElementById("psw2-div");
    var pswMsg=document.getElementById("psw2-msg");
    var idcodeDiv=document.getElementById("idcode2-div");
    var idcodeMsg1=document.getElementById("idcode2-msg1");
    var idcodeMsg2=document.getElementById("idcode2-msg2");
    var file=$("#file").val();
    var fileDiv=document.getElementById("file-div");
    var fileMsg=document.getElementById("file-msg");
    if (!file){
        alert("请上传文件");
        return;
    }
    var fileName1 = file.substring(file.lastIndexOf(".") + 1).toLowerCase();
    var size = $("input[name='fileName']")[0].files[0].size;
    if((fileName1 != "jpg" && fileName1 !="img"&&fileName1!="png")||size>10485760){
        fileDiv.classList.add("has-error");
        fileMsg.classList.remove("sr-only");
        emailDiv.classList.remove("has-error");
        emailMsg1.classList.add("sr-only");
        emailMsg2.classList.add("sr-only");
        usernameDiv.classList.remove("has-error");
        usernameMsg.classList.add("sr-only");
        pswDiv.classList.remove("has-error");
        pswMsg.classList.add("sr-only");
        idcodeDiv.classList.remove("has-error");
        idcodeMsg1.classList.add("sr-only");
        idcodeMsg2.classList.add("sr-only");
        return false;
    }
    var formData = new FormData();
    formData.append("file",$("#file")[0].files[0]);
    var avatarUrl='';
    var imgUrl=$("#imgUrl");
    $.ajax({
        type : "post",
        url : "/register/uploadFile",
        data : formData,
        processData : false,
        contentType : false,
        success : function(data){
            if (data){
                avatarUrl=data;
                alert("上传成功");
                $("#avatarUrl").val(avatarUrl);
                imgUrl.attr("src",avatarUrl);
            }else {
                alert("上传失败");
            }
        }
    });

}

function register() {
    var email=$("#email2").val();
    var username=$("#userName").val();
    var psw=$("#password2").val();
    var verifyCode=$("#idCode2").val();
    var emailDiv=document.getElementById("email2-div");
    var emailMsg1=document.getElementById("email2-msg1");
    var emailMsg2=document.getElementById("email2-msg2");
    var usernameDiv=document.getElementById("username-div");
    var usernameMsg=document.getElementById("username-msg");
    var pswDiv=document.getElementById("psw2-div");
    var pswMsg=document.getElementById("psw2-msg");
    var idcodeDiv=document.getElementById("idcode2-div");
    var idcodeMsg1=document.getElementById("idcode2-msg1");
    var idcodeMsg2=document.getElementById("idcode2-msg2");
    var fileDiv=document.getElementById("file-div");
    var fileMsg=document.getElementById("file-msg");
    var avatarUrl=$("#avatarUrl").val();
    if (!email) {
        alert("请输入邮箱");
        return;
    }
    if (!username) {
        alert("请输入用户名");
        return;
    }
    if (!psw) {
        alert("请输入密码");
        return;
    }
    if (!avatarUrl){
        alert("请上传文件");
        return;
    }
    if (!verifyCode) {
        alert("请输入验证码");
        return;
    }
    if (username.length < 6 || username.length > 18) {
        fileDiv.classList.remove("has-error");
        fileMsg.classList.add("sr-only");
        emailDiv.classList.remove("has-error");
        emailMsg1.classList.add("sr-only");
        emailMsg2.classList.add("sr-only");
        usernameDiv.classList.add("has-error");
        usernameMsg.classList.remove("sr-only");
        pswDiv.classList.remove("has-error");
        pswMsg.classList.add("sr-only");
        idcodeDiv.classList.remove("has-error");
        idcodeMsg1.classList.add("sr-only");
        idcodeMsg2.classList.add("sr-only");
        return ;
    }
    if (psw.length < 6 || psw.length > 18) {
        fileDiv.classList.remove("has-error");
        fileMsg.classList.add("sr-only");
        emailDiv.classList.remove("has-error");
        emailMsg1.classList.add("sr-only");
        emailMsg2.classList.add("sr-only");
        usernameDiv.classList.remove("has-error");
        usernameMsg.classList.add("sr-only");
        pswDiv.classList.add("has-error");
        pswMsg.classList.remove("sr-only");
        idcodeDiv.classList.remove("has-error");
        idcodeMsg1.classList.add("sr-only");
        idcodeMsg2.classList.add("sr-only");
    }

    $.ajax({
        type: "POST",
        url: "/register",
        contentType: "application/json",
        data: JSON.stringify({
            "email":email,
            "password":psw,
            "verifyCode":verifyCode,
            "name":username,
            "avatarUrl":avatarUrl
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                console.log(response);
            } else if (response.code == 203) {
                fileDiv.classList.remove("has-error");
                fileMsg.classList.add("sr-only");
                emailDiv.classList.add("has-error");
                emailMsg1.classList.remove("sr-only");
                emailMsg2.classList.add("sr-only");
                usernameDiv.classList.remove("has-error");
                usernameMsg.classList.add("sr-only");
                pswDiv.classList.remove("has-error");
                pswMsg.classList.add("sr-only");
                idcodeDiv.classList.remove("has-error");
                idcodeMsg1.classList.add("sr-only");
                idcodeMsg2.classList.add("sr-only");
            }else if (response.code == 202) {
                fileDiv.classList.remove("has-error");
                fileMsg.classList.add("sr-only");
                emailDiv.classList.remove("has-error");
                emailMsg1.classList.add("sr-only");
                emailMsg2.classList.add("sr-only");
                usernameDiv.classList.remove("has-error");
                usernameMsg.classList.add("sr-only");
                pswDiv.classList.remove("has-error");
                pswMsg.classList.add("sr-only");
                idcodeDiv.classList.add("has-error");
                idcodeMsg1.classList.remove("sr-only");
                idcodeMsg2.classList.add("sr-only");
            }else if (response.code == 201) {
                fileDiv.classList.remove("has-error");
                fileMsg.classList.add("sr-only");
                emailDiv.classList.remove("has-error");
                emailMsg1.classList.add("sr-only");
                emailMsg2.classList.add("sr-only");
                usernameDiv.classList.remove("has-error");
                usernameMsg.classList.add("sr-only");
                pswDiv.classList.remove("has-error");
                pswMsg.classList.add("sr-only");
                idcodeDiv.classList.add("has-error");
                idcodeMsg1.classList.add("sr-only");
                idcodeMsg2.classList.remove("sr-only");
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

// function selectTopTag(e) {
//     var id=$("#new");
//     id.removeClass("well");
//     var id1=$("#active");
//     id1.removeClass("well");
//     e.classList.add("well");
//     var type=e.getAttribute("type");
//     // $.ajax({
//     //     type: "post",
//     //     url: "/index/selectType",
//     //     contentType: "application/json",
//     //     data: JSON.stringify({
//     //
//     //         "type": type
//     //
//     //     }),
//     //     success: function (response) {
//     //         if (response.code == 200) {
//     //             window.location.reload();
//     //             console.log(response);
//     //         } else {
//     //             alert(response.message);
//     //         }
//     //         console.log(response);
//     //     },
//     //     dataType: "json"
//     // });
// }

