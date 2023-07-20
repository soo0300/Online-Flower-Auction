package com.kkoch.admin.domain.plant;

import com.kkoch.admin.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@Getter
@Entity
public class Plant extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code")
    private Category code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "name")
    private Category name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private Category type;

    private boolean active;

    @Builder
    public Plant(Long id, Category code, Category name, Category type, boolean active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.active = active;
    }
}
