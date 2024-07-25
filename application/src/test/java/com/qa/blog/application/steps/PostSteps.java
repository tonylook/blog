package com.qa.blog.application.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.blog.mariadb.AuthorEntity;
import com.qa.blog.mariadb.CategoryEntity;
import com.qa.blog.mariadb.PostEntity;
import com.qa.blog.mariadb.TagEntity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AutoConfigureWebTestClient
public class PostSteps extends BaseSteps {

    @Given("the {string} category and the {string} author exist:")
    public void theCategoryAndTheAuthorExist(String categoria, String autore) {
        AuthorEntity authorEntity = new AuthorEntity(null, autore);
        CategoryEntity categoryEntity = new CategoryEntity(null, categoria);
        TagEntity tagEntity1 = new TagEntity(null, "Default Tag 1");
        TagEntity tagEntity2 = new TagEntity(null, "Default Tag 2");

        Set<TagEntity> tagEntities = new HashSet<>();
        tagEntities.add(tagEntity1);
        tagEntities.add(tagEntity2);

        PostEntity postEntity = new PostEntity(
            null,
            authorEntity,
            categoryEntity,
            tagEntities,
            "Default Title",
            "Default content",
            "http://example.com/default.jpg"
        );
        resultComponent.post=jpaPostRepository.save(postEntity);
    }

    @When("I create a new blog post with the following details:")
    public void i_create_a_new_blog_post_with_the_following_details(DataTable dataTable) throws Exception {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            List<String> tagsList = List.of(columns.get("tags").split(", "));

            PostRequest postRequest = new PostRequest(
                columns.get("title"),
                columns.get("content"),
                columns.get("author"),
                columns.get("image"),
                columns.get("category"),
                tagsList
            );

            String requestBody = new ObjectMapper().writeValueAsString(postRequest);

            resultComponent.actualResponse = webTestClient.post().uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange();
        }
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatus) {
        resultComponent.actualResponse.expectStatus().isEqualTo(expectedStatus);
    }

    @Then("the response should contain the new blog post with:")
    public void the_response_should_contain_the_new_blog_post_with(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {

            resultComponent.actualResponse.expectBody()
                .jsonPath("$.title").isEqualTo(columns.get("title"))
                .jsonPath("$.content").isEqualTo(columns.get("content"))
                .jsonPath("$.author").isEqualTo(columns.get("author"))
                .jsonPath("$.image").isEqualTo(columns.get("image"))
                .jsonPath("$.category").isEqualTo(columns.get("category"))
                .jsonPath("$.tags[0]").isNotEmpty();
        }
    }

    @And("the response should contain an error message {string}")
    public void theResponseShouldContainAnErrorMessage(String error) {
        resultComponent.actualResponse.expectBody()
            .jsonPath("$.errorMessage").isEqualTo(error);
    }

    public record PostRequest(String title, String content, String author, String image, String category, List<String> tags) {
    }
}
