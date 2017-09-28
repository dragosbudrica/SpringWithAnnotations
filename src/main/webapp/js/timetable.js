var Timetable = Timetable || {};

$(document).ready(function () {
    Timetable.getEvents();
});

Timetable.getEvents = function getEvents() {
    var index;
    var courses = [];
    $.ajax({
        url: '/getEvents',
        type: 'GET',
        dataType: 'json',
        traditional: true,
        success: function (data) {
            if(data.code === 0) {
                for(index = 0; index < data.length; ++index) {
                    courses.push({'title' : data[index].courseName, 'start' : new Date(data[index].startTime), 'end': new Date(data[index].endTime)});
                }
                Timetable.displayEvents(courses);
            } else if(data.locale === 'en') {
                $("#calendar").hide();
                $("#imageEn").show();
            } else if(data.locale === 'fr') {
                $("#calendar").hide();
                $("#imageFr").show();
            } else if(data.locale === 'ro') {
                $("#calendar").hide();
                $("#imageRo").show();
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
};

Timetable.displayEvents = function displayEvents(courses) {
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listWeek'
        },
        navLinks: true, // can click day/week names to navigate views
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        events:  courses
    });
};
