package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.domain.Member;
import study.datajpa.dto.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);


//    @Query(name="Member.findByUsername")
    List<Member> findByUsername(@Param("us ername")String username);


    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username")String username, @Param("age")int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto( m.id,m.username,t.name) from Member m join m.team t")
    List<MemberDto> findByDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names")List<String> names);


    List<Member> findMemberByUsername(String username);
    Optional<Member> findOptionalByUsername(String username);
    @Query(value = "select m from Member m left join m.team t",
                countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying
    @Query("update Member m set m.age =m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age")int age);
}
