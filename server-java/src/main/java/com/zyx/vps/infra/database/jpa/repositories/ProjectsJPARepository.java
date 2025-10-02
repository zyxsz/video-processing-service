package com.zyx.vps.infra.database.jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.errors.NotfoundException;
import com.zyx.vps.application.repositories.ProjectsRepository;
import com.zyx.vps.infra.database.jpa.entities.JPAProject;
import com.zyx.vps.infra.database.jpa.mappers.ProjectMapper;
import com.zyx.vps.infra.database.jpa.mappers.UserMapper;

interface JPAProjectsRepository extends JpaRepository<JPAProject, String> {
  // JPAUser save(JPAUser user);

  // Optional<JPAUser> findByEmail(String email);

  Optional<JPAProject> findById(String id);
}

public class ProjectsJPARepository implements ProjectsRepository {

  @Autowired
  private JPAProjectsRepository repository;

  @Override
  public List<Project> findByNameAndUserId(String name, String userId) {

    JPAProject jpaProjectExample = new JPAProject(null);

    jpaProjectExample.setId(null);
    jpaProjectExample.setName(name);
    jpaProjectExample.setUser(UserMapper.fromId(userId));

    ExampleMatcher matcher = ExampleMatcher.matching()
        .withMatcher("name", m -> m.ignoreCase())
        .withMatcher("userId", m -> m.ignoreCase());

    Example<JPAProject> example = Example.of(jpaProjectExample, matcher);

    List<JPAProject> jpaProjects = this.repository.findAll(example);

    return jpaProjects.stream().map(project -> (Project) project).toList();
  }

  @Override
  public Project findById(String id) {
    Optional<JPAProject> jpaProject = this.repository.findById(id);

    if (jpaProject.isEmpty())
      return null;

    Project project = jpaProject.get();

    return project;
  }

  @Override
  public List<Project> findByUserId(String userId, Sort sort) {
    JPAProject jpaProjectExample = new JPAProject(null);

    jpaProjectExample.setId(null);

    jpaProjectExample.setUser(UserMapper.fromId(userId));

    ExampleMatcher matcher = ExampleMatcher.matching()
        .withMatcher("userId", m -> m.ignoreCase());

    Example<JPAProject> example = Example.of(jpaProjectExample, matcher);

    List<JPAProject> jpaProjects = this.repository.findAll(example, sort);

    return jpaProjects.stream().map(project -> (Project) project).toList();
  }

  @Override
  public Project findByIdAndUserId(String id, String userId) {
    JPAProject jpaProjectExample = new JPAProject();

    jpaProjectExample.setId(id);
    jpaProjectExample.setUser(UserMapper.fromId(userId));

    Example<JPAProject> example = Example.of(jpaProjectExample);

    Optional<JPAProject> jpaProject = this.repository.findOne(example);

    if (jpaProject.isEmpty())
      return null;

    return jpaProject.get();
  }

  @Override
  public void create(Project project) {
    // JPAProject jpaProject = ProjectMapper.fromProject(project);

    this.repository.save(ProjectMapper.toJPA(project));

    return;
  }

  @Override
  public void save(Project project) {

    this.repository.save(ProjectMapper.toJPA(project));

    return;
  }

  @Override
  public void delete(Project project) {
    this.repository.deleteById(project.getId());

    return;
  }

  @Override
  public int countUserProjects(String userId) {
    JPAProject jpaProjectExample = new JPAProject(null);

    jpaProjectExample.setId(null);
    jpaProjectExample.setUser(UserMapper.fromId(userId));

    Example<JPAProject> example = Example.of(jpaProjectExample);

    long count = this.repository.count(example);

    return Math.toIntExact(count);
  }

  @Override
  public void validateProjectByUserId(String projectId, String userId) {
    JPAProject jpaProjectExample = new JPAProject(null);

    jpaProjectExample.setId(projectId);
    jpaProjectExample.setUser(UserMapper.fromId(userId));

    Example<JPAProject> example = Example.of(jpaProjectExample);

    Optional<JPAProject> project = this.repository.findOne(example);

    if (project.isEmpty())
      throw new NotfoundException("Project not found");

    return;
  }

}
