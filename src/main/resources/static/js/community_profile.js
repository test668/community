function deleteQuestion(e) {
    var confirm=window.confirm("确定删除该条提问？");
    if(confirm){
        var questionId=e.getAttribute("question-id");
        $.ajax({
            type: "POST",
            url: "/question/delete",
            contentType: "application/json",
            data: JSON.stringify({
                "id": questionId
            }),
            success: function (response) {
                if (response.code == 200) {
                    console.log(response);
                    alert(response.message);
                    window.location.reload();
                } else {
                    alert(response.message);
                }
                console.log(response);
            },
            dataType: "json"
        });
    }
}

function deleteUnread(e) {
    var userId=e.getAttribute("user-id");
    $.ajax({
        type: "POST",
        url: "/notification/deleteUnread",
        contentType: "application/json",
        data: JSON.stringify({
            "receiver": userId
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

