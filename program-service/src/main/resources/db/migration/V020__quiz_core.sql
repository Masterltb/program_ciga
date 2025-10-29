-- ENUMs
do $$ begin
    if not exists (select 1 from pg_type t join pg_namespace n on n.oid=t.typnamespace
                   where t.typname = 'quiz_template_status' and n.nspname = 'program') then
create type program.quiz_template_status as enum ('DRAFT','PUBLISHED','ARCHIVED');
end if;
    if not exists (select 1 from pg_type t join pg_namespace n on n.oid=t.typnamespace
                   where t.typname = 'attempt_status' and n.nspname = 'program') then
create type program.attempt_status as enum ('OPEN','SUBMITTED');
end if;
end $$;

-- TEMPLATES
create table if not exists program.quiz_templates(
                                                     id uuid primary key,
                                                     name text not null,
                                                     version integer not null default 1,
                                                     status program.quiz_template_status not null default 'DRAFT',
                                                     language_code text,
                                                     published_at timestamptz,
                                                     archived_at timestamptz,
                                                     scope text,          -- 'system'|'coach' (nếu không dùng có thể bỏ)
                                                     owner_id uuid,       -- người tạo (admin/coach)
                                                     created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
    );

create table if not exists program.quiz_template_questions(
                                                              template_id uuid not null references program.quiz_templates(id) on delete cascade,
    question_no integer not null,
    text text not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    primary key (template_id, question_no)
    );

create table if not exists program.quiz_choice_labels(
                                                         template_id uuid not null,
                                                         question_no integer not null,
                                                         score integer not null,
                                                         label text not null,
                                                         primary key (template_id, question_no, score),
    foreign key (template_id, question_no)
    references program.quiz_template_questions(template_id, question_no)
    on delete cascade
    );

-- ASSIGNMENTS
create table if not exists program.quiz_assignments(
                                                       id uuid primary key,
                                                       template_id uuid not null references program.quiz_templates(id),
    program_id uuid not null references program.programs(id),
    every_days integer not null default 5,
    created_by uuid,
    scope text,
    created_at timestamptz not null default now()
    );
create index if not exists idx_qas_program on program.quiz_assignments(program_id);

-- ATTEMPTS & ANSWERS
create table if not exists program.quiz_attempts(
                                                    id uuid primary key,
                                                    program_id uuid not null references program.programs(id),
    template_id uuid not null references program.quiz_templates(id),
    user_id uuid not null,
    opened_at timestamptz not null,
    submitted_at timestamptz,
    status program.attempt_status not null default 'OPEN'
    );
create index if not exists idx_qatt_program_template on program.quiz_attempts(program_id, template_id, status);

create table if not exists program.quiz_answers(
                                                   attempt_id uuid not null references program.quiz_attempts(id) on delete cascade,
    question_no integer not null,
    answer integer not null,
    created_at timestamptz not null default now(),
    primary key (attempt_id, question_no)
    );

-- RESULTS
create table if not exists program.quiz_results(
                                                   id uuid primary key,
                                                   program_id uuid not null references program.programs(id),
    template_id uuid not null references program.quiz_templates(id),
    quiz_version integer not null,
    total_score integer not null,
    severity program.severity_level not null,
    created_at timestamptz not null default now()
    );
create index if not exists idx_qres_program_template on program.quiz_results(program_id, template_id, created_at desc);
