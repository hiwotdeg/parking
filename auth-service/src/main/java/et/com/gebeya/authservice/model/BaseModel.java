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
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_on")
    private Instant createdOn;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Instant updatedOn;


    @Column(name = "is_active")
    private Boolean isActive;
}