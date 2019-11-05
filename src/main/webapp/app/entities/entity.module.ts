import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'subject',
        loadChildren: () => import('./subject/subject.module').then(m => m.LearningPatternsSubjectModule)
      },
      {
        path: 'theme',
        loadChildren: () => import('./theme/theme.module').then(m => m.LearningPatternsThemeModule)
      },
      {
        path: 'subtheme',
        loadChildren: () => import('./subtheme/subtheme.module').then(m => m.LearningPatternsSubthemeModule)
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.LearningPatternsActivityModule)
      },
      {
        path: 'group',
        loadChildren: () => import('./group/group.module').then(m => m.LearningPatternsGroupModule)
      },
      {
        path: 'student-schedule',
        loadChildren: () => import('./student-schedule/student-schedule.module').then(m => m.LearningPatternsStudentScheduleModule)
      },
      {
        path: 'teacher',
        loadChildren: () => import('./teacher/teacher.module').then(m => m.LearningPatternsTeacherModule)
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.LearningPatternsStudentModule)
      },
      {
        path: 'student-activity',
        loadChildren: () => import('./student-activity/student-activity.module').then(m => m.LearningPatternsStudentActivityModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LearningPatternsEntityModule {}
