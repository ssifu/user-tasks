## APIs
### Authentication APIs:
- `POST /auth/register`: For registering the users
- `POST /auth/login`: For user login and take back JWT token
### User APIs:
- `GET /users/`: To get all the users
- `GET /users/tasks`: To get tasks of a specific user (username extracted from JWT token)
### Task APIs:
- `POST /tasks`: To create a task
- `POST /tasks?task_id=<value>&username=<value>`: To assign a task to a user
- `PUT /tasks/update/status?task_id=<value>&username=<value>`: To update a tasks status
