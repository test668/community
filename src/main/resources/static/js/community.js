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

function login() {

}
var countdown = 60;
function setEmail(obj) {

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
        setEmail(obj)
    },1000)

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

