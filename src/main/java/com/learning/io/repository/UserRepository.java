package com.learning.io.repository;

import com.learning.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserId(String userId);

    @Query(value="Select * from Users u where u.EMAIL_VERIFICATION_STATUS = true",
            countQuery="select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = true",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress( Pageable pageableRequest );

    @Query(value = "Select * from Users u where u.FIRST_NAME = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    @Query(value = "Select * from Users u where u.LAST_NAME = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

    @Query(value ="Select * from Users u where u.FIRST_NAME LIKE %:keyword% or u.LAST_NAME LIKE %:keyword%" , nativeQuery = true)
    List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);

    @Query(value ="Select u.FIRST_NAME,u.LAST_NAME from Users u where u.FIRST_NAME LIKE %:keyword% or u.LAST_NAME LIKE %:keyword%" , nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(value = "update users u set u.EMAIL_VERIFICATION_STATUS =:emailVerificationStatus where u.User_Id =:userId",nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus,
                                           @Param("userId") String userId);

    @Query("Select user from UserEntity user where user.userId =:userId")
    UserEntity findUserEntityByUserId(@Param("userId") String userId);

    @Query("select user.firstName,user.lastName from UserEntity user where user.userId =:userId")
    List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("Update UserEntity u set u.emailVerificationStatus = :emailVerificationStatus where u.userId = :userId")
    void updateUserEntityEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerification,
                                                 @Param("userId") String userId);
}
