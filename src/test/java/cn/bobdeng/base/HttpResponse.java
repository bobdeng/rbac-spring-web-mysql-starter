package cn.bobdeng.base;

import lombok.Getter;
import okhttp3.Response;

import java.io.IOException;

@Getter
public class HttpResponse {
    private int code;
    private String content;

    public HttpResponse(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public HttpResponse(Response response) throws IOException {
        this.code = response.code();
        this.content = response.body().string();
    }
}
