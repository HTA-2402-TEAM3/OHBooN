package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.dao.UserDao;

import java.util.List;
import java.util.Map;

public class UserService {
    public void setEvaluation(List<Map<String, String>> evalList) {
        UserDao userDao = new UserDao();
        MatchDAO matchDAO = new MatchDAO();
        for (Map<String, String> map : evalList) {
            userDao.setEvaluation(map);
            matchDAO.setEvaluation(map.get("matchID"));
        }
    }
}
