package ro.cadeca.vitele.persistence.subject

import ro.cadeca.vitele.persistence.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class SubjectEntity(
        @Column
        var name: String,

        @Column
        var code: String,

        @Column
        var description: String,

        @Column
        var semester: Int
) : BaseEntity()
