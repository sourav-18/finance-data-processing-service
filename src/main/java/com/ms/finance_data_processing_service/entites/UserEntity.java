package com.ms.finance_data_processing_service.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users",
        indexes = {
            @Index(name = "idx_user_role",columnList = "role"),
            @Index(name = "idx_role_status",columnList = "status")
        }
)
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String profilePicture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusType status=UserStatusType.Active;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", updatable = false)
    private UserEntity createdBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;
}
