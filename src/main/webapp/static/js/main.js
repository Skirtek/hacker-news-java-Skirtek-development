const BASE_URL = window.location.origin;
const newsDiv = document.querySelector("#news");
const prevButton = document.querySelector("#prev");
const nextButton = document.querySelector("#next");
const menuElements = document.querySelectorAll(".nav-link");

async function fetchData(queryString) {
    console.log(`Requested route ${queryString}`);
    window.history.pushState(null, null, queryString);

    // http://localhost:8080/hackernews_war_exploded/api?category=news&page=1
    const fetchUrl = `${BASE_URL}/hackernews_war_exploded/api${queryString}`
    console.log(`Fetching from ${fetchUrl}`);

    /*    fetch(fetchUrl).then(response => response.json()).then().catch(error -> {

        });*/

    try {
        const response = await fetch(fetchUrl);
        const deserializedResponse = await response.json();
        renderNews(deserializedResponse);
    } catch (error) {
        console.log(error);
        newsDiv.innerHTML = "<div class='alert alert-danger' role='alert'>Something went wrong</div>"
    }
}

async function onMenuItemClicked(event) {
    const element = event.target;
    const category = element.dataset.category

    event.preventDefault()

    if (category) {
        await fetchData(`?category=${category}&page=1`)
    }
}

async function onPageChange(event) {
    const queryParams = new URLSearchParams(window.location.search);
    const nextPageValue = event.target.id === 'prev' ? -1 : 1;

    const newPage = parseInt(queryParams.get("page")) + nextPageValue

    if (newPage < 1) {
        return;
    }

    queryParams.set("page", newPage);

    await fetchData(`?${queryParams.toString()}`)
}

function renderNews(news) {
    newsDiv.innerHTML = "";

    news.forEach((newsItem) => {
        const card = document.createElement("div");
        card.setAttribute("class", "card-body");

        const cardTitle = document.createElement("div");
        cardTitle.setAttribute("class", "card-title");

        const cardHref = document.createElement("a");
        cardHref.setAttribute("href", newsItem.url);
        cardHref.setAttribute("target", "_blank");
        cardHref.innerText = newsItem.title;

        cardTitle.appendChild(cardHref);
        card.appendChild(cardTitle);

        const cardText = document.createElement("p");
        cardText.setAttribute("class", "card-text");
        cardText.innerHTML = `${newsItem.time_ago}<br/>${newsItem.user}`;

        card.appendChild(cardText);

        const cardElement = document.createElement('div');
        cardElement.classList.add("card")
        cardElement.classList.add("text-white")
        cardElement.classList.add("bg-dark")
        cardElement.classList.add("mb-3")
        cardElement.appendChild(card);

        newsDiv.appendChild(cardElement)
    });
}

function bindEventListeners() {
    menuElements.forEach(menuItem => menuItem.addEventListener('click', onMenuItemClicked))

    prevButton.addEventListener('click', onPageChange);
    nextButton.addEventListener('click', onPageChange);
}

(async function () {
    bindEventListeners()
    const queryString = "?category=news&page=1"
    await fetchData(queryString);
})();