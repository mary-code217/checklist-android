function startLoading() {
    const loader = document.getElementById('page-loader');
    const content = document.getElementById('page-content');
    if (loader) loader.classList.remove('is-hidden');
    if (content) content.classList.add('is-hidden');
}

function stopLoading() {
    const loader = document.getElementById('page-loader');
    const content = document.getElementById('page-content');
    if (loader) loader.classList.add('is-hidden');
    if (content) content.classList.remove('is-hidden');
}
