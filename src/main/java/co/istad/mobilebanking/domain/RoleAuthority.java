package co.istad.mobilebanking.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="role_authorities")
public class RoleAuthority {
    @Id
    private Integer id;
    @Column(unique = true, nullable = false)
    private Integer authority_id;
    @ManyToOne
    private Role role;

}
