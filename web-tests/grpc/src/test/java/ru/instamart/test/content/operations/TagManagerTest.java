package ru.instamart.test.content.operations;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseId;
import tagmanager.TagManagerGrpc;
import tagmanager.Tagmanager;

import java.util.UUID;

import static org.testng.Assert.*;

@Epic("Tag Manager Microservice")
@Feature("Tag Manager")
public class TagManagerTest extends GrpcBase {
    private TagManagerGrpc.TagManagerBlockingStub client;
    private final String ownerName = UUID.randomUUID().toString();
    private final String ownerDescription = "test description";
    private int ownerId;
    private final String tagName = UUID.randomUUID().toString();
    private int tagId;


    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        client = TagManagerGrpc.newBlockingStub(channel);
    }

    @Story("Владелец")
    @CaseId(3)
    @Test(description = "Создание владельца с валидными данными",
            groups = {"grpc-tag-manager"})
    public void createOwner() {
        var request = Tagmanager.CreateOwnerRequest
                .newBuilder()
                .setName(ownerName)
                .setDescription(ownerDescription)
                .build();

        var response = client.createOwner(request);

        check.id(response.getId());
        check.equals(response.getName(), ownerName);
        check.equals(response.getDescription(), ownerDescription);

        ownerId = response.getId();
    }

    @Story("Владелец")
    @CaseId(4)
    @Test(description = "Создание владельца с существующим именем",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createOwner",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "ALREADY_EXISTS: Owner with similar name already exists: " +
                    "\\{ID:\\d+ Name:[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12} Description:test description}")
    public void createDuplicateOwner() {
        var request = Tagmanager.CreateOwnerRequest
                .newBuilder()
                .setName(ownerName)
                .setDescription(ownerDescription)
                .build();

        client.createOwner(request);
    }

    @Story("Владелец")
    @CaseId(8)
    @Test(description = "Получение списка владельцев",
            groups = {"grpc-tag-manager"})
    public void getOwners() {
        var request = Tagmanager.GetOwnersRequest.newBuilder().build();

        var owners = client.getOwners(request).getOwnersList();

        for (var owner : owners) {
            check.id(owner.getId());
            check.notNull(owner.getName());
            check.notNull(owner.getDescription());
        }
    }

    @Story("Тэг")
    @CaseId(17)
    @Test(description = "Создание тега с валидными данными",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createOwner")
    public void createTag() {
        var request = Tagmanager.CreateTagRequest.newBuilder()
                .setName(tagName)
                .setOwnerId(ownerId)
                .build();

        var response = client.createTag(request);

        check.id(response.getId());
        check.equals(response.getName(), tagName);
        check.equals(response.getOwnerId(), ownerId);

        tagId = response.getId();
    }

    @Story("Тэг")
    @CaseId(18)
    @Test(description = "Создание тега с существующим именем",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createTag",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "ALREADY_EXISTS: Tag with similar name already exists: " +
                    "\\{ID:\\d+ Name:[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12} OwnerID:\\d+}")
    public void createDuplicateTag() {
        var request = Tagmanager.CreateTagRequest.newBuilder()
                .setName(tagName)
                .setOwnerId(ownerId)
                .build();

        client.createTag(request);
    }

    @Story("Тэг")
    @CaseId(24)
    @Test(description = "Создание тега с несуществующим id владельца",
            groups = {"grpc-tag-manager"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create tag: ERROR: insert or update on table \"tags\" " +
                    "violates foreign key constraint \"tags_owner_id_fkey\" \\(SQLSTATE 23503\\)")
    public void createTagWithUnknownOwnerId() {
        var request = Tagmanager.CreateTagRequest.newBuilder()
                .setName(UUID.randomUUID().toString())
                .setOwnerId(Integer.MAX_VALUE)
                .build();

        client.createTag(request);
    }

    @Story("Тэг")
    @CaseId(31)
    @Test(description = "Удаление существующего тега",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "getTagsByOwner")
    public void deleteTag() {
        var request = Tagmanager.DeleteTagRequest.newBuilder()
                .setTagId(tagId)
                .build();

        var response = client.deleteTag(request);
        assertTrue(response.getOk());
    }

    @Story("Тэг")
    @CaseId(26)
    @Test(description = "Получение списка тегов владельца",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createDuplicateTag")
    public void getTagsByOwner() {
        var request = Tagmanager.GetTagsByOwnerRequest.newBuilder()
                .setOwnerId(ownerId)
                .build();

        var tag = client.getTagsByOwner(request).getTags(0);

        check.id(tag.getId());
        check.equals(tag.getName(), tagName);
        check.equals(tag.getOwnerId(), ownerId);
    }

    @Story("Тэг")
    @CaseId(29)
    @Test(description = "Получение списка тегов владельца по несуществующему id владельца",
            groups = {"grpc-tag-manager"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot find owner by id")
    public void getTagsByUnknownOwner() {
        var request = Tagmanager.GetTagsByOwnerRequest.newBuilder()
                .setOwnerId(Integer.MAX_VALUE)
                .build();

        client.getTagsByOwner(request);
    }

    @Story("Тэг")
    @CaseId(35)
    @Test(description = "Привязка тега к сущности с валидными данными",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createTag")
    public void bindTag() {
        var request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(tagId)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId("something")
                        .setMeta("something")
                        .build())
                .build();

        var response = client.bindTag(request);
        assertTrue(response.getOk());
    }

    @Story("Тэг")
    @CaseId(38)
    @Test(description = "Привязка тега с существующей привязкой",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "bindTag")
    public void bindTagDuplicate() {
        String itemId = "something";
        String meta = "something";

        var request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(tagId)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId)
                        .setMeta(meta)
                        .build())
                .build();

        var response = client.bindTag(request);
        assertTrue(response.getOk());

        var bind = grpc.getBindsByTag(tagId).get(0);
        check.equals(bind.getTagId(), tagId);
        check.equals(bind.getItemId(), itemId);
        check.equals(bind.getMeta(), meta);

        request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(tagId)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId)
                        .setMeta("something new")
                        .build())
                .build();

        response = client.bindTag(request);
        assertTrue(response.getOk());

        bind = grpc.getBindsByTag(tagId).get(0);
        check.equals(bind.getTagId(), tagId);
        check.equals(bind.getItemId(), itemId);
        check.equals(bind.getMeta(), "something new");
    }
}

