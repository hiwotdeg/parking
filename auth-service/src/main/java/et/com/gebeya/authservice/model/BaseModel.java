package et.com.gebeya.authservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
//@Builder
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(columnDefinition = "serial")
    private Integer id;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_on")
    private Instant createdOn;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Instant updatedOn;

    @JsonIgnore
    @Column(name = "is_active")
//    @NotBlank(message = "ActiveStatus is mandatory")
    private Boolean isActive;
}