# APIs
## Authentication APIs:
### Register
- **URL:** `/auth/register`
- **Method:** `POST`
- **Access:** `USER`, `ADMIN`
- **Request Body:**
```json
{
    "username": username,
    "password": password,
    "roles": ["ROLE1", "ROLE2", ...]
}
```
### Login
- **URL:** `/auth/login`
- **Method:** `POST`
- **Access:** `USER`, `ADMIN`
- **Request Body:**
```json
{
    "username": username,
    "password": password
}
```

## User APIs:
## Tasks of a specific user
- **URL:** `/users/tasks`
- **Access:** `USER`, `ADMIN`
- **Method:** `GET`

## Admin APIs:
### Get all the users
- **URL:** `/admin/users`
- **Method:** `GET`
- **Access:** `ADMIN`

### Create an admin account
- **URL:** `/admin/create`
- **Method:** `POST`
- **Access:** `ADMIN`
- **Request Body:**
```json
{
    "username": username,
    "password": password,
    "roles": ["ROLE1", "ROLE2", ...]
}
```

### Update user to admin
- **URL:** `/admin/update-user?user_id=userId`
- **Method:** `PUT`
- **Access:** `ADMIN`

## Task APIs:
### Get all the tasks
- **URL:** `/tasks/`
- **Method:** `GET`
- **Access:** `ADMIN`

### Create a task
- **URL:** `/tasks`
- **Method:** `POST`
- **Access:** `USER`, `ADMIN` 
  - ADMIN can assign the task to anyone and USER can only create task for themselves
- **Request Body:**
```json
{
    "taskId": "1",
    "assignedTo": "samiulsifat",
    "title": "title",
    "description": "desciption",
    "dueDate": "01/01/2025"
}
```

### Assign a task
- **URL:** `/tasks/assign?task_id=taskId&username=username`
- **Method:** `PUT`
- **Access:** `ADMIN`

### Update a task status
- **URL:** `/tasks/update/status?task_id=taskId&username=username`
- **Method:** `PUT`
- **Access:** `USER`, `ADMIN`
