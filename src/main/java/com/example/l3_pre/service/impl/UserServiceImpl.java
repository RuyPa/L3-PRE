package com.example.l3_pre.service.impl;

import com.example.l3_pre.dto.response.UserDetailsInfoResp;
import com.example.l3_pre.dto.response.UserResp;
import com.example.l3_pre.exception.NotFoundException;
import com.example.l3_pre.service.UserService;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

import static com.example.l3_pre.consts.Mapper.USER_ACCOUNT_MAPPER;
import static com.example.l3_pre.consts.Mapper.USER_MAPPER;
import static com.example.l3_pre.consts.ProcedureName.User.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final EntityManager entityManager;

    @Override
    public List<UserResp> getAllLeaders() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_LEADERS, USER_MAPPER);
        return castResultSetQueryToUsers(query);
    }

    private List<UserResp> castResultSetQueryToUsers(StoredProcedureQuery query) {
        List<UserResp> userResps = new ArrayList<>();
        for (Object object : query.getResultList()) {
            userResps.add((UserResp) object);
        }
        return userResps;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_ACCOUNT_EXISTENCE, USER_ACCOUNT_MAPPER)
                .registerStoredProcedureParameter(USERNAME, String.class, ParameterMode.IN)
                .setParameter(USERNAME, username);
        return castResultSetToUser(query);
    }

    private UserDetailsInfoResp castResultSetToUser(StoredProcedureQuery query) {
        List<?> objects = query.getResultList();
        checkResultSetEmpty(objects);
        UserResp result = new UserResp();
        result.setRoleRespList(new ArrayList<>());
        for (Object object : objects) {
            UserResp userResp = (UserResp) object;
            result.setId(userResp.getId());
            result.setUsername(userResp.getUsername());
            result.setPassword(userResp.getPassword());
            result.getRoleRespList().add(userResp.getRoleRespList().get(0));
        }
        return new UserDetailsInfoResp(result);
    }

    private void checkResultSetEmpty(List<?> objects) {
        if (ObjectUtils.isEmpty(objects)) {
            throw new NotFoundException(ErrorMessageConstant.NOT_FOUND, new ApiMessageError(USER_NOT_FOUND));
        }
    }
}
