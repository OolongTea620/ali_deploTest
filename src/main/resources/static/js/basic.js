// basic.js

// 공통으로 사용할 API 요청 함수
function sendRequest(method, url, data, callback) {
    var token = localStorage.getItem("Access_Token");
    var xhr = new XMLHttpRequest();
    xhr.open(method, url);
    xhr.setRequestHeader("Authorization", token);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onload = function() {
        if (xhr.status === 200 || xhr.status === 201) {
            var Access_Token = xhr.getResponseHeader("Access_Token");
            if (Access_Token) {
                localStorage.setItem("Access_Token", Access_Token);
            }

            var Refresh_Token = xhr.getResponseHeader("Refresh_Token");
            if (Refresh_Token) {
                localStorage.setItem("Refresh_Token", Refresh_Token);
            }
        }
        callback(xhr);
    };
    xhr.send(JSON.stringify(data));
}

// 다른 파일에서 사용할 수 있도록 export
export { sendRequest };