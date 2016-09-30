package se.plushogskolan.casemanagement.service;

import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Issue;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.model.WorkItem;
import se.plushogskolan.casemanagement.repository.IssueRepository;
import se.plushogskolan.casemanagement.repository.TeamRepository;
import se.plushogskolan.casemanagement.repository.UserRepository;
import se.plushogskolan.casemanagement.repository.WorkItemRepository;

/**
 * - En User måste ha ett användarnamn som är minst 10 tecken långt.
 * - När en User inaktivares ändras status på alla dennes WorkItem till
 * Unstarted
 * - Det får max vara 10 users i ett team
 * - En User kan bara ingå i ett team åt gången
 * - En WorkItem kan inte tilldelas en User som är inaktiv
 * - En User kan max ha 5 WorkItems samtidigt
 * - Ett Issue ska bara kunna läggas till work item som har status Done
 * - När en Issue läggs till en work item ändras status för work item till
 * Unstarted
 */

public final class CaseService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final WorkItemRepository workItemRepository;
    private final IssueRepository issueRepository;

    public CaseService(UserRepository userRepository, TeamRepository teamRepository,
            WorkItemRepository workItemRepository, IssueRepository issueRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.workItemRepository = workItemRepository;
        this.issueRepository = issueRepository;
    }

    public void saveUser(User user) {
        // TODO - En User måste ha ett användarnamn som är minst 10 tecken långt
        // Det får max vara 10 users i ett team
        if (usernameLongEnough(user.getUsername()) && teamHasSpace(user.getId(), user.getTeamId())) {
            userRepository.saveUser(user);
        }
    }

    public void updateUser(User newValues) {
        // TODO - En User måste ha ett användarnamn som är minst 10 tecken långt
        // Det får max vara 10 users i ett team

        if (usernameLongEnough(newValues.getUsername()) && teamHasSpace(newValues.getId(), newValues.getTeamId())) {
            userRepository.updateUser(newValues);
        }

    }

    public void inactivateUserById(int userId) {
        // TODO - När en User inaktiveras ändras status på alla dennes WorkItem
        // till Unstarted
        userRepository.inactivateUserById(userId);
        setStatusOfAllWorkItemsOfUserToUnstarted(userId);
    }

    public User getUserById(int userId) {
        try {
            return userRepository.getUserById(userId);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public User getUserBy(String firstName, String lastName, String username) {
        return userRepository.getUserBy(firstName, lastName, username);
    }

    public List<User> getUsersByTeamId(int teamId) {
        return userRepository.getUsersByTeamId(teamId);
    }

    public void saveTeam(Team team) {
        try {
            teamRepository.saveTeam(team);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateTeam(Team newValues) {
        try {
            teamRepository.updateTeam(newValues);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void inactivateTeam(int teamId) {
        try {
            teamRepository.inactivateTeam(teamId);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Team> getAllTeams() {
        try {
            return teamRepository.getAllTeams();
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void addUserToTeam(int userId, int teamId) {
        // TODO Det får max vara 10 users i ett team
        if (teamHasSpace(userId, teamId)) {
            try {
                teamRepository.addUserToTeam(userId, teamId);
            } catch (RepositoryException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void saveWorkItem(WorkItem workItem) {
        // TODO - En WorkItem kan inte tilldelas en User som är inaktiv
        //
        try {
            workItemRepository.saveWorkItem(workItem);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateStatusById(int workItemId, WorkItem.Status workItemStatus) {
        try {
            workItemRepository.updateStatusById(workItemId, workItemStatus);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO
    }

    // Save for last
    public void deleteWorkItem(int workItemId) {
        // TODO
        try {
            workItemRepository.deleteWorkItem(workItemId);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addWorkItemToUser(int workItemId, int userId) {
        // TODO
        try {
            workItemRepository.addWorkItemToUser(workItemId, userId);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<WorkItem> getWorkItemsByStatus(WorkItem.Status workItemStatus) {
        // TODO
        try {
            return workItemRepository.getWorkItemsByStatus(workItemStatus);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkItem> getWorkItemsByTeamId(int teamId) {
        // TODO
        try {
            return workItemRepository.getWorkItemsByTeamId(teamId);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkItem> getWorkItemsByUserId(int userId) {
        // TODO
        try {
            return workItemRepository.getWorkItemsByUserId(userId);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkItem> getWorkItemsWithIssue() {
        // TODO
        try {
            return workItemRepository.getWorkItemsWithIssue();
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void saveIssue(Issue issue) {
        // TODO
        issueRepository.saveIssue(issue);
    }

    public void updateIssue(Issue newValues) {
        // TODO
        issueRepository.updateIssue(newValues);
    }

    private boolean usernameLongEnough(String username) {
        // TODO - En User måste ha ett användarnamn som är minst 10 tecken långt
        return username.length() >= 10;
    }

    private boolean teamHasSpace(int userId, int teamId) {
        // TODO Det får max vara 10 users i ett team
        // throws Repository exception if no user by that id is found
        // which means that it is a save operation and not an update operation
        // Consider adding a boolean userExists(userId) method to hide
        // implementation details
        try {
            User user = userRepository.getUserById(userId);
            if (user.getTeamId() == teamId) {
                return true;
            } else {
                return numberOfUsersInTeamLessThanTen(teamId);
            }
        } catch (RepositoryException e) {
            return numberOfUsersInTeamLessThanTen(teamId);
        }
    }

    private boolean numberOfUsersInTeamLessThanTen(int teamId) {

        List<User> users = userRepository.getUsersByTeamId(teamId);
        return users.size() < 10;
    }

    private void setStatusOfAllWorkItemsOfUserToUnstarted(int userId) {
        // TODO - När en User inaktiveras ändras status på alla dennes WorkItem
        List<WorkItem> workItems;
        try {
            workItems = workItemRepository.getWorkItemsByUserId(userId);
            for (WorkItem workItem : workItems) {
                workItemRepository.updateStatusById(workItem.getId(), WorkItem.Status.UNSTARTED);
            }
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}