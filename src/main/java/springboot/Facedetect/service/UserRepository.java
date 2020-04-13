package springboot.Facedetect.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import springboot.Facedetect.entity.UserInfo;

@Repository
public interface UserRepository extends MongoRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);

}
