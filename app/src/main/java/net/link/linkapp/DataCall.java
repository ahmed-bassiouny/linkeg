package net.link.linkapp;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataCall {

    private static String connection = "Check Connection";
    private static String serverError = "Please call developer team";

    public static void signIn(String email, String password, final Result result){
        Call<BaseResponse> responseCall =  Config.httpApiInterface.login(email,password);
        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.body() == null){
                    result.failed(serverError);
                }else if(!response.body().isStatus()){
                    result.failed(response.body().getMessage());
                }else {
                    result.success(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                result.failed(t.getLocalizedMessage());
            }
        });
    }

    public static void signUp(String name , File img , String email, String password,
                              String phone,String type,String category,final Result result){
        Call<BaseResponse> responseCall =  Config.httpApiInterface.signup(
                Utils.convertFileToPart(img),
                Utils.convertStringToPart(name),
                Utils.convertStringToPart(email),
                Utils.convertStringToPart(password),
                Utils.convertStringToPart(phone),
                Utils.convertStringToPart(type),
                Utils.convertStringToPart(category)

        );
        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.body() == null){
                    result.failed(serverError);
                }else if(!response.body().isStatus()){
                    result.failed(response.body().getMessage());
                }else {
                    result.success(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                result.failed(t.getLocalizedMessage());
            }
        });
    }
}
