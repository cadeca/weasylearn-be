package ro.cadeca.weasylearn.persistence.user

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserDocument, String> {
    fun findByUsername(username: String): UserDocument?
}