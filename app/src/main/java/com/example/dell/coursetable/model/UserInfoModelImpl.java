package com.example.dell.coursetable.model;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Kyrie
 * @version 1.0
 * @description   用户信息model接口
 * @date 2018/2/10
 */

public interface UserInfoModelImpl {
    public void initDataById(String id)throws IOException, ExecutionException, InterruptedException ;
    public void initDataByName(String userName)throws IOException, ExecutionException , InterruptedException ;
    public void initDataByStudentNumber(String studentNumber)throws IOException, ExecutionException , InterruptedException ;
    public boolean createUser(String studentNumber , String userName  )throws IOException ,ExecutionException, InterruptedException;
    public boolean updateUser(String studentNumber , String userName ,String email ,  Integer integral , String area , String nickName  )throws IOException ,ExecutionException, InterruptedException;
}
