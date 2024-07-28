package com.qa.blog.application.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.blog.mariadb.AuthorEntity;
import com.qa.blog.mariadb.CategoryEntity;
import com.qa.blog.mariadb.PostEntity;
import com.qa.blog.mariadb.TagEntity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@AutoConfigureWebTestClient
public class PostSteps extends BaseSteps {
    private String userType;

    @After
    public void afterEachScenario() {
        deleteDatabase();
    }
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
        resultComponent.post = jpaPostRepository.save(postEntity);
    }

    @When("I create a new blog post with the following details:")
    public void i_create_a_new_blog_post_with_the_following_details(DataTable dataTable) throws Exception {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            List<String> tagsList = List.of(columns.get("tags").split(","));

            PostRequest postRequest = new PostRequest(
                columns.get("title"),
                columns.get("content"),
                columns.get("author"),
                columns.get("image"),
                columns.get("category"),
                tagsList
            );

            String requestBody = new ObjectMapper().writeValueAsString(postRequest);

            resultComponent.actualResponse = webTestClient.post().uri("/post")
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

    @Then("the response should contain:")
    public void the_response_should_contain_the_new_blog_post_with(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            String[] tagsArray = columns.get("tags").split(",");
            resultComponent.actualResponse.expectBody()
                .jsonPath("$.title").isEqualTo(columns.get("title"))
                .jsonPath("$.content").isEqualTo(columns.get("content"))
                .jsonPath("$.author").isEqualTo(columns.get("author"))
                .jsonPath("$.image").isEqualTo(columns.get("image"))
                .jsonPath("$.category").isEqualTo(columns.get("category"))
                .jsonPath("$.tags[*]").value(containsInAnyOrder(tagsArray));
        }
    }

    @And("the response should contain an error message {string}")
    public void theResponseShouldContainAnErrorMessage(String error) {
        resultComponent.actualResponse.expectBody()
            .jsonPath("$.errorMessage").isEqualTo(error);
    }

    @When("I update the blog post to have:")
    public void iUpdateTheBlogPostToHave(DataTable dataTable) throws Exception {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            String jsonPatchString = "[";
            if (columns.containsKey("title")) {
                jsonPatchString += "{\"op\": \"replace\", \"path\": \"/title\", \"value\": \"" + columns.get("title") + "\"},";
            }
            if (columns.containsKey("content")) {
                jsonPatchString += "{\"op\": \"replace\", \"path\": \"/content\", \"value\": \"" + columns.get("content") + "\"},";
            }
            if (columns.containsKey("author")) {
                jsonPatchString += "{\"op\": \"replace\", \"path\": \"/author\", \"value\": \"" + columns.get("author") + "\"},";
            }
            if (columns.containsKey("image")) {
                jsonPatchString += "{\"op\": \"replace\", \"path\": \"/image\", \"value\": \"" + columns.get("image") + "\"},";
            }
            if (columns.containsKey("category")) {
                jsonPatchString += "{\"op\": \"replace\", \"path\": \"/category\", \"value\": \"" + columns.get("category") + "\"},";
            }
            if (columns.containsKey("tags")) {
                jsonPatchString += "{\"op\": \"replace\", \"path\": \"/tags\", \"value\": " + new ObjectMapper().writeValueAsString(List.of(columns.get("tags").split(","))) + "},";
            }
            jsonPatchString = jsonPatchString.replaceAll(",$", "") + "]";

            resultComponent.actualResponse = webTestClient.patch().uri("/post/" + resultComponent.post.getId())
                .contentType(MediaType.valueOf("application/json-patch+json"))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPatchString)
                .exchange();
        }
    }

    @When("I update the blog post to add tag {string}")
    public void iUpdateTheBlogPostToAddTag(String tag) {
        PostEntity existingPost = resultComponent.post;
        String jsonPatchString = "[{\"op\": \"add\", \"path\": \"/tags/-\", \"value\": \"" + tag + "\"}]";

        resultComponent.actualResponse = webTestClient.patch().uri("/post/" + existingPost.getId())
            .contentType(MediaType.valueOf("application/json-patch+json"))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(jsonPatchString)
            .exchange();
    }

    @When("I update the blog post to remove tag {string}")
    public void iUpdateTheBlogPostToRemoveTags(String tagToRemove) {
        PostEntity existingPost = resultComponent.post;
        String jsonPatchString = "[{\"op\": \"remove\", \"path\": \"/tags/-\", \"value\": \"" + tagToRemove + "\"}]";
        resultComponent.actualResponse = webTestClient.patch().uri("/post/" + existingPost.getId())
            .contentType(MediaType.valueOf("application/json-patch+json"))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(jsonPatchString)
            .exchange();
    }

    @Given("the following blog posts exist:")
    public void aBlogPostExistsWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            AuthorEntity authorEntity = new AuthorEntity(null, columns.get("author"));
            CategoryEntity categoryEntity = new CategoryEntity(null, columns.get("category"));
            List<String> tagsList = List.of(columns.get("tags").split(","));
            Set<TagEntity> tagEntities = new HashSet<>();
            for (String tag : tagsList) {
                tagEntities.add(new TagEntity(null, tag));
            }
            PostEntity postEntity = new PostEntity(
                null,
                authorEntity,
                categoryEntity,
                tagEntities,
                columns.get("title"),
                columns.get("content"),
                columns.get("image")
            );
            resultComponent.post = jpaPostRepository.save(postEntity);
        }
    }

    @When("I search for blog posts with title {string}")
    public void iSearchForBlogPostsWithTitle(String title) {
        resultComponent.actualResponse = webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/post")
                .queryParam("title", title)
                .build())
            .exchange();
    }

    @When("I search for blog posts with category {string}")
    public void iSearchForBlogPostsWithCategory(String category) {
        resultComponent.actualResponse = webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/post")
                .queryParam("category", category)
                .build())
            .exchange();
    }

    @When("I search for blog posts with tags {string}")
    public void iSearchForBlogPostsWithTags(String tags) {
        resultComponent.actualResponse = webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/post")
                .queryParam("tags", tags)
                .build())
            .exchange();
    }

    @When("I search for blog posts with title {string}, category {string}, and tags {string}")
    public void iSearchForBlogPostsWithTitleCategoryAndTags(String title, String category, String tags) {
        resultComponent.actualResponse = webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/post")
                .queryParam("title", title)
                .queryParam("category", category)
                .queryParam("tags", tags)
                .build())
            .exchange();
    }

    @And("the response should contain {int} results")
    public void theResponseShouldContainResults(int postsQuantity) {
        resultComponent.actualResponse.expectBody()
            .jsonPath("$.length()").isEqualTo(postsQuantity);
    }

    @Given("I am an {string}")
    public void iAmAn(String userType) {
        this.userType = userType;
    }

    @When("I delete the blog post")
    public void iDeleteTheBlogPost() {
        resultComponent.actualResponse = webTestClient.delete()
            .uri("/post/" + resultComponent.post.getId())
            .header("X-user", userType)
            .exchange();
    }

    @Then("the blog post should no longer exist")
    public void theBlogPostShouldNoLongerExist() {
        boolean postExists = jpaPostRepository.existsById(resultComponent.post.getId());
        assertFalse(postExists, "The blog post should no longer exist");
    }

    public record PostRequest(String title, String content, String author, String image, String category,
                              List<String> tags) {
    }
}
