package ro.cadeca.weasylearn.converters.user

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.ConversionException
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER
import kotlin.reflect.full.cast


@Converter
class UserDocumentToTeacherModelConverter : IConverter<UserDocument, Teacher> {
    override fun convert(source: UserDocument): Teacher {
        if (source.type != TEACHER)
            throw ConversionException("Could not convert document to teacher: $source")

        val teacher = Teacher(source.username, source.firstName, source.lastName, source.dateOfBirth, source.profilePicture, source.email)

        source.details.let { details ->
            details["department"]?.let { teacher.department = it as String }
            details["titles"]?.let { teacher.titles = if (it is List<*>) it.map(String::class::cast) else null }
            details["eduUser"]?.let { teacher.eduUser = it as String }
            details["githubUser"]?.let { teacher.githubUser = it as String }
        }

        return teacher
    }
}
