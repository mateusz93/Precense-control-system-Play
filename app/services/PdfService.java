package services;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import models.Grade;
import models.StudentCourse;
import models.TeacherCourse;
import models.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import play.i18n.Messages;
import play.mvc.Http;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PdfService {

    private Messages messages;

    public byte[] generateGradesPdf(User user) {
        log.info("Start generating pdf with grades for user: ", user.getFullName());
        try(PDDocument document = new PDDocument()) {
            messages = Http.Context.current().messages();
            PDPage blankPage = new PDPage();
            document.addPage(blankPage);
            setProperties(document);
            try(PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0))) {
                printTitle(contentStream);
                printUserData(user, contentStream);
                printGrades(user, contentStream);
            }
            document.save("grades.pdf");
            log.info("Pdf generated");
            return Files.readAllBytes(Paths.get("grades.pdf"));
        } catch (IOException e) {
            log.error("Pdf generation error", e);
            return new byte[0];
        }
    }

    private void setProperties(PDDocument document) {
        log.debug("Setting properties");
        PDDocumentInformation pdd = document.getDocumentInformation();
        pdd.setAuthor("Presence Control System");
        pdd.setTitle("Grade List");
        pdd.setCreator("Mateusz Wieczorek");
        pdd.setSubject("Grade List");
        Calendar date = new GregorianCalendar();
        LocalDate currentDate = LocalDate.now();
        date.set(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        pdd.setCreationDate(date);
    }

    private void printTitle(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 18);
        contentStream.setLeading(14f);
        contentStream.newLineAtOffset(250, 710);
        contentStream.showText(messages.at("pdf.gradeList"));
        //contentStream.endText();
    }

    private void printUserData(User user, PDPageContentStream contentStream) throws IOException {
        //contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 13);
        contentStream.setLeading(14f);
        contentStream.newLineAtOffset(-200, -40);
        contentStream.showText(messages.at("pdf.firstAndLastname") + ": " + user.getFullName());
        contentStream.newLine();
        contentStream.showText(messages.at("pdf.index") + ": " + user.getId());
        contentStream.newLine();
        contentStream.showText(messages.at("pdf.group") + ": " + user.getGroup());
        contentStream.newLine();
        contentStream.showText(messages.at("pdf.yearOfStudy") + ": " + user.getYearOfStudy());
        //contentStream.endText();
    }

    private void printGrades(User user, PDPageContentStream contentStream) throws IOException {
        //contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
        contentStream.setLeading(14f);
        contentStream.newLineAtOffset(0, -50);
        contentStream.showText(StringUtils.rightPad(messages.at("pdf.subjectName"), 50, " "));
        contentStream.showText(StringUtils.rightPad(messages.at("pdf.grades"), 70, " "));
        contentStream.showText(messages.at("pdf.finalGrade"));
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
        contentStream.newLineAtOffset(0, -10);
        for (PdfDto dto : getUserGrades(user)) {
            contentStream.newLineAtOffset(0, -18);
            contentStream.showText(StringUtils.rightPad(dto.getSubjectName(), 60, " "));
            contentStream.showText(StringUtils.rightPad(dto.getGrades(), 95, " "));
            contentStream.showText(dto.getFinalGrade());
        }
        contentStream.endText();
    }

    private List<PdfDto> getUserGrades(User user) {
        log.debug("Preparing grades to print");
        List<PdfDto> data = Lists.newArrayList();
        List<StudentCourse> studentCourses = StudentCourse.findByStudent(user);
        studentCourses.forEach(it -> {
            TeacherCourse teacherCourse = TeacherCourse.findOne(it.getTeacherCourse().getId());
            PdfDto dto = new PdfDto();
            dto.setSubjectName(teacherCourse.getSubject().getName());
            dto.setGrades(Grade.findByTeacherCourse(teacherCourse)
                    .stream()
                    .filter(grade -> !grade.isFinalGrade())
                    .map(grade -> String.valueOf(grade.getValue()))
                    .collect(Collectors.joining(", ")));
            val finalGrades = Grade.findByTeacherCourse(teacherCourse)
                    .stream()
                    .filter(Grade::isFinalGrade)
                    .map(grade -> String.valueOf(grade.getValue()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(finalGrades)) {
                dto.setFinalGrade(finalGrades.get(0));
            }
        });
        return data;
    }

//    private List<PdfDto> getFakeUserGrades(User user) {
//        List<PdfDto> data = Lists.newArrayList();
//        for (int i = 0; i < 20; i++) {
//            PdfDto dto = new PdfDto();
//            dto.setSubjectName("Podstawy programowania");
//            dto.setGrades("4,3,2,3,6,4,3,4,5,3,4,5,3");
//            dto.setFinalGrade("5");
//            data.add(dto);
//        }
//        return data;
//    }

    @Data
    private class PdfDto {
        private String subjectName;
        private String grades;
        private String finalGrade;
    }
}
