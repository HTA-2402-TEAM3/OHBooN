package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.ChatDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

import java.util.List;

public class ChatRoomDAO {

    public List<ChatDTO> getChatList(String senderName) {
        List<ChatDTO> chatList;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        chatList = sqlSession.selectList("getChatList", senderName);
        sqlSession.commit();
        sqlSession.close();
        return chatList;
    }

    //    create, delete, update
public List<ChatDTO> getChatRoom() {

    SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
    List<ChatDTO> ChatDTOs = sqlSession.selectList("getChatRoom"); // 리스트로 수정할것
    sqlSession.commit();
    sqlSession.close();
    return ChatDTOs;
}

    public int CreateChatRoom (ChatDTO chatDTO) {
    int result;
    SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        try {
            result = sqlSession.insert("createChatRoom", chatDTO);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();;
            throw e;
        }finally {
            sqlSession.close();
        }
        return result;
    }

    public int updateChatRoom(ChatDTO chatDTO) {
        int result;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateChatRoom",chatDTO);
        sqlSession.commit();
        sqlSession.close();
        return result;
    }
    public int deleteChatroom(ChatDTO chatDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.delete("deleteChatRoom",chatDTO);
        System.out.println("dao result==="+result);
        sqlSession.commit();
        sqlSession.close();
        return result;
    }
}
