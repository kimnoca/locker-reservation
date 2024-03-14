package yu.cse.locker.domain.locker.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Locker {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id")
    private Long lockerId;

    // 주인 아이디 외래키
    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "room_location")
    private int roomLocation;

    @Column(name = "locker_row")
    private int row;

    @Column(name = "locker_column")
    private int column;

}
